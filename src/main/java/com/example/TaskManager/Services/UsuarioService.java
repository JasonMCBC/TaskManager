package com.example.TaskManager.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TaskManager.dto.UserDTO;
import com.example.TaskManager.dto.UserRequest;
import com.example.TaskManager.model.Rol;
import com.example.TaskManager.model.Usuario;
import com.example.TaskManager.repository.RolRepository;
import com.example.TaskManager.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO registrarUsuario(UserRequest request) {

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(obtenerRolPorDefecto())
                .build();

        Usuario guardado = usuarioRepository.save(usuario);

        return new UserDTO(guardado.getId(), guardado.getUsername());
    }

    private Set<Rol> obtenerRolPorDefecto() {
        Set<Rol> roles = new HashSet<>();
        Rol rolUser = rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado"));
        roles.add(rolUser);
        return roles;
    }

    public List<UserDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(u -> new UserDTO(u.getId(), u.getUsername()))
                .toList();
    }
}
