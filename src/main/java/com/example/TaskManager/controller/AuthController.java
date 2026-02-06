package com.example.taskmanager.controller;

import com.example.taskmanager.Services.UsuarioService;
import com.example.taskmanager.dto.LoginRequest;
import com.example.taskmanager.dto.LoginResponse;
import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.dto.UserRequest;
import com.example.taskmanager.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;  // ← NUEVO

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequest request) {
        UserDTO usuario = userService.registrarUsuario(request);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // 1. Autenticar usuario (verifica username y password)
        // Si falla, lanza BadCredentialsException automáticamente
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Si llegó aquí, las credenciales son correctas
        // 3. Generar el JWT
        String token = jwtUtil.generateToken(request.getUsername());

        // 4. Retornar el token al cliente
        LoginResponse response = new LoginResponse(
            token,
            "Bearer",
            request.getUsername()
        );

        return ResponseEntity.ok(response);
    }
}