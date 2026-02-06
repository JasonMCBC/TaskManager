package com.example.taskmanager.mapper;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.dto.UserRequest;
import com.example.taskmanager.model.Usuario;

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
