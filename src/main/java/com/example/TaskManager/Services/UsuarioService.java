package com.example.TaskManager.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TaskManager.dto.UsuarioDTO;
import com.example.TaskManager.dto.UsuarioRequest;
import com.example.TaskManager.mapper.UsuarioMapper;
import com.example.TaskManager.model.Rol;
import com.example.TaskManager.model.Usuario;
import com.example.TaskManager.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO registrarUsuario(UsuarioRequest request) {
        // Validamos que el usuario no exista
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        // 1. Convertimos Request → Entidad
        Usuario usuario = UsuarioMapper.toEntity(request);

        // 2. Asignamos rol por defecto
        usuario.setRol(Rol.USER);

        // 3. Encriptamos la contraseña ANTES de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // 4. Guardamos en la base de datos
        Usuario guardado = usuarioRepository.save(usuario);

        // 5. Devolvemos DTO (sin password)
        return UsuarioMapper.toDTO(guardado);
    }
}