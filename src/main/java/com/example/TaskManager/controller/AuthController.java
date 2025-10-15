package com.example.TaskManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TaskManager.Services.UserService;
import com.example.TaskManager.dto.LoginRequest;
import com.example.TaskManager.dto.UserDTO;
import com.example.TaskManager.dto.UserRequest;



@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(UserService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequest request) {
        UserDTO usuario = usuarioService.registrarUsuario(request);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("✅ Login correcto para usuario: " + request.getUsername());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }
}
