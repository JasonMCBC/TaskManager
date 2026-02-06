package com.example.taskmanager.security;

import com.example.taskmanager.Services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Extraer el header Authorization
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 2. Verificar que el header exista y empiece con "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Quitar "Bearer "
            
            try {
                // 3. Extraer el username del token
                username = jwtUtil.getUsernameFromToken(jwt);
            } catch (Exception e) {
                // Token inválido o expirado
                logger.error("Error extrayendo username del JWT: " + e.getMessage());
            }
        }

        // 4. Si tenemos username y NO hay autenticación previa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 5. Cargar los detalles del usuario desde la DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 6. Validar el token
            if (jwtUtil.validateToken(jwt, userDetails)) {
                
                // 7. Crear objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,  // Credenciales (no las necesitamos aquí)
                        userDetails.getAuthorities()  // Roles del usuario
                    );
                
                authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 8. Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 9. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}