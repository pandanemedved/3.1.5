package ru.kata.spring.boot_security.demo.conroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userPage(Model model, Principal principal) {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user.orElse(null));
        return "user";
    }
}
