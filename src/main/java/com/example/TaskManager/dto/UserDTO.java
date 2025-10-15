package com.example.TaskManager.dto;

import java.util.Set;

import com.example.TaskManager.model.Rol;

public class UserDTO{

    private final Long id;
    private final String username;
    private final Set<Rol> rol;

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
        this.rol = null;
    }

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public Set<Rol> getRol() {
        return rol;
    }
    
}