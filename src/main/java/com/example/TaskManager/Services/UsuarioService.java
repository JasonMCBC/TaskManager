package com.example.TaskManager.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TaskManager.dto.UsuarioDTO;
import com.example.TaskManager.dto.UsuarioRequest;
import com.example.TaskManager.mapper.UsuarioMapper;
import com.example.TaskManager.model.Usuario;
import com.example.TaskManager.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO registrarUsuario(UsuarioRequest request) {
        // 1. Convertimos DTO → Entidad
        Usuario usuario = UsuarioMapper.toEntity(request);

        // 2. Encriptamos la contraseña ANTES de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // 3. Guardamos en la base de datos
        Usuario guardado = usuarioRepository.save(usuario);

        // 4. Devolvemos DTO (sin password)
        return UsuarioMapper.toDTO(guardado);
    }
}