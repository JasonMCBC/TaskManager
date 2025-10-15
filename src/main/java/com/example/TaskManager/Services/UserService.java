package com.example.TaskManager.Services;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TaskManager.dto.UserDTO;
import com.example.TaskManager.dto.UserRequest;
import com.example.TaskManager.mapper.UserMapper;
import com.example.TaskManager.model.Rol;
import com.example.TaskManager.model.Usuario;
import com.example.TaskManager.repository.RolRepository;
import com.example.TaskManager.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    public UserService(UserRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registrarUsuario(UserRequest request) {
        // Validamos que el usuario no exista
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        // 1. Convertimos Request → Entidad
        Usuario usuario = UserMapper.toEntity(request);

        // 2. Asignamos rol por defecto
        Rol rolUsuario = rolRepository.findByNombre("USER")
                .orElseGet(() -> rolRepository.save(new Rol(null, "USER")));

        usuario.setRoles(new HashSet<>(List.of(rolUsuario)));

        // 3. Encriptamos la contraseña ANTES de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // 4. Guardamos en la base de datos
        Usuario guardado = usuarioRepository.save(usuario);

        // 5. Devolvemos DTO (sin password)
        return UserMapper.toDTO(guardado);
    }

     public List<UserDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}