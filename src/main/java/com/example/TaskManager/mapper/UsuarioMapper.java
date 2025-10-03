package com.example.TaskManager.mapper;

import com.example.TaskManager.dto.UsuarioDTO;
import com.example.TaskManager.dto.UsuarioRequest;
import com.example.TaskManager.model.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword()); // luego se cifrará en el service
        return usuario;
    }

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRol()
        );
    }
}
