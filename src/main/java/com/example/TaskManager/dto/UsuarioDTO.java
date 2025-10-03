package com.example.TaskManager.dto;

import com.example.TaskManager.model.Rol;

public class UsuarioDTO{

    private final Long id;
    private final String username;
    private final Rol rol;

    public UsuarioDTO(Long id, String username, Rol rol) {
        this.id = id;
        this.username = username;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public Rol getRol() {
        return rol;
    }
    
}