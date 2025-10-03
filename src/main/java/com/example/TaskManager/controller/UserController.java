package com.example.TaskManager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TaskManager.Services.UsuarioService;
import com.example.TaskManager.dto.UsuarioDTO;
import com.example.TaskManager.dto.UsuarioRequest;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UsuarioService usuarioService;

    public UserController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioRequest request) {
        UsuarioDTO usuario = usuarioService.registrarUsuario(request);
        return ResponseEntity.ok(usuario);
    }
}
