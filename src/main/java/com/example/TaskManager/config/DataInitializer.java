package com.example.taskmanager.config;

import com.example.taskmanager.model.Rol;
import com.example.taskmanager.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializador de datos para la aplicación.
 * Se ejecuta automáticamente al arrancar Spring Boot.
 * Crea los roles necesarios si no existen.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    @Override
    public void run(String... args) {
        log.info("🔄 Verificando roles en la base de datos...");
        
        initializeRoles();
        
        log.info("✅ Inicialización de datos completada");
    }

    /**
     * Crea los roles necesarios si no existen en la base de datos
     */
    private void initializeRoles() {
        // Crear rol USER si no existe
        if (rolRepository.findByNombre("ROLE_USER").isEmpty()) {
            Rol roleUser = new Rol();
            roleUser.setNombre("ROLE_USER");
            rolRepository.save(roleUser);
            log.info("✅ Rol ROLE_USER creado");
        } else {
            log.info("ℹ️  Rol ROLE_USER ya existe");
        }

        // Crear rol ADMIN si no existe
        if (rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
            Rol roleAdmin = new Rol();
            roleAdmin.setNombre("ROLE_ADMIN");
            rolRepository.save(roleAdmin);
            log.info("✅ Rol ROLE_ADMIN creado");
        } else {
            log.info("ℹ️  Rol ROLE_ADMIN ya existe");
        }
    }
}