package com.rafael.todolist.controller;

import com.rafael.todolist.entity.User;
import com.rafael.todolist.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Controller responsible for user authentication.
 * Handles user registration and login page rendering.
 */
@Controller
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Registers a new user.
     * <p>
     * Checks if the username already exists, encodes the password,
     * and saves the user in the database.
     *
     * @param username the username provided by the user
     * @param password the raw password to be encoded
     * @param model    the Spring MVC model
     * @return redirect to login page if success, otherwise returns register view with error
     */
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

    /**
     * Displays the registration page.
     *
     * @return the register view
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Thymeleaf template
    }

    /**
     * Displays the login page.
     *
     * @return the login view
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Thymeleaf template
    }


}
