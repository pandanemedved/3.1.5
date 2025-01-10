package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String roleName) {
        Optional<Role> roles = roleRepository.findByRoleName(roleName);
        return roles.orElseThrow(() -> new RuntimeException("Not found role: " + roleName));
    }
}
