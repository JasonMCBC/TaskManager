package com.example.taskmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.dto.UserRequest;
import com.example.taskmanager.mapper.UserMapper;
import com.example.taskmanager.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UsuarioRepository userRepository;

    public UserController(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    @GetMapping //GET Todas
    public List<UserDTO> listar(){
        return userRepository.findAll()
            .stream()
            .map(UserMapper::toDTO)
            .toList();
    }

    // ✅ GET por id
    @GetMapping("/{id}")
    public UserDTO obtener(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElse(null);
    }

    // ✅ PUT (actualizar)
    @PutMapping("/{id}")
    public UserDTO actualizar(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return userRepository.findById(id)
                .map(t -> {
                    t.setUsername(request.getUsername());
                    t.setPassword(request.getPassword());
                    return UserMapper.toDTO(userRepository.save(t));
                })
                .orElse(null);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
