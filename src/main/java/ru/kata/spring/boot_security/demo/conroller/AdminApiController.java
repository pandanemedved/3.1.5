package ru.kata.spring.boot_security.demo.conroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserConverter;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    private final UserService userService;
    private final UserConverter userConverter;

    public AdminApiController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers();
        return ResponseEntity.ok(userDTOS);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userConverter.convert(userDTO));
        return ResponseEntity.ok("User added");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("{\"message\":\"success\"}");
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }
}
