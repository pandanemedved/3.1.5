package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    private final RoleRepository roleRepository;

    public UserConverter(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User convert(UserDTO userDTO) {
        Set<Role> roles = userDTO.getRoles().stream()
                .filter(role -> role.getId() != null)
                .map(role -> roleRepository.findById(role.getId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Role with id %s not found", role.getId()))))
                .collect(Collectors.toSet());
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setUserage(userDTO.getUserage());
        user.setRoles(roles);
        return user;
    }
}
