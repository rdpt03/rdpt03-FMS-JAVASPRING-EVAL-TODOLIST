package com.rafael.todolist.controller;

import com.rafael.todolist.entity.User;
import com.rafael.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/index")
    public String home(Authentication auth, Model model) {
        //check id user is connected
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            //get user name
            String username = auth.getName();
            //get user
            User user = userRepo.findByUsername(username).orElseThrow();

            //send to front
            model.addAttribute("user", user);
            model.addAttribute("title", "Accueil - TDL");
            return "index";
        }
    }
}