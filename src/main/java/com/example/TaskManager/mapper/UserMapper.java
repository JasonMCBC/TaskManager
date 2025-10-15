package com.example.TaskManager.mapper;

import com.example.TaskManager.dto.UserDTO;
import com.example.TaskManager.dto.UserRequest;
import com.example.TaskManager.model.Usuario;

public class UserMapper {

    public static Usuario toEntity(UserRequest request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword()); // luego se cifrará en el service
        return usuario;
    }

    public static UserDTO toDTO(Usuario usuario) {
        return new UserDTO(
                usuario.getId(),
                usuario.getUsername()
        );
    }
}
