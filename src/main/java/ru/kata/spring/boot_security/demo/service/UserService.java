package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, Map<String, Object> update) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        update.forEach((key, value) -> {
            switch (key) {
                case "username":
                    user.setUsername((String) value);
                    break;
                case "firstname":
                    user.setFirstname((String) value);
                    break;
                case "lastname":
                    user.setLastname((String) value);
                    break;
                case "userage":
                    user.setUserage((Integer) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "roles":
                    if (value instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> rolesData = (List<Map<String, Object>>) value;
                        Set<Role> roles = rolesData.stream()
                                .map(roleData -> {
                                    Number id1 = (Number) roleData.get("id");
                                    return roleRepository.findById(id1.longValue())
                                            .orElseThrow(EntityNotFoundException::new);
                                })
                                .collect(Collectors.toSet());
                        user.setRoles(roles);
                        break;
                    } else {
                        throw new IllegalArgumentException("Wrong parameter");
                    }
            }
        });
        userRepository.save(user);
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userRepository.getById(id);
        return new UserDTO(user);
    }
}
