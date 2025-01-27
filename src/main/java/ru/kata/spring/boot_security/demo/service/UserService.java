package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
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
    public void updateUser(Long id, UserDTO userDTO) {
        User oldUser = userRepository.getById(id);

        oldUser.setUsername(userDTO.getUsername());
        oldUser.setFirstname(userDTO.getFirstname());
        oldUser.setLastname(userDTO.getLastname());
        oldUser.setEmail(userDTO.getEmail());
        oldUser.setUserage(userDTO.getUserage());
        Set<Role> updateRoles = userDTO.getRoles().stream()
                        .map(role -> roleRepository.getById(role.getId()))
                .collect(Collectors.toSet());
        oldUser.setRoles(updateRoles);

        userRepository.save(oldUser);
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
