package ru.kata.spring.boot_security.demo.conroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam List<String> roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String roleName : roles) {
            Role role = roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user.getId(), user);
        return "redirect:/admin";
    }
}
