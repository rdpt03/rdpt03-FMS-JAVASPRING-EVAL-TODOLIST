package com.rafael.todolist.controller;

import com.rafael.todolist.entity.User;
import com.rafael.todolist.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    //---------------------REGISTER--------------------------
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {

        if (repo.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Usuário já existe");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        repo.save(user);

        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Thymeleaf template
    }

    //-----------------------login--------------------------
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Thymeleaf template
    }


}
