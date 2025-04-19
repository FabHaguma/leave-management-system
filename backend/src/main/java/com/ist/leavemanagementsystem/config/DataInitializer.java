package com.ist.leavemanagementsystem.config;

import com.ist.leavemanagementsystem.model.Role;
import com.ist.leavemanagementsystem.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        String[] roles = {"STAFF", "MANAGER", "ADMIN"};
        for (String role : roles) {
            if (roleRepository.findByName(role) == null) {
                roleRepository.save(new Role(role));
            }
        }
    }
}