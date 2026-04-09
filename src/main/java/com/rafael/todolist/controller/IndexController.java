package com.rafael.todolist.controller;

import com.rafael.todolist.entity.Category;
import com.rafael.todolist.entity.Task;
import com.rafael.todolist.entity.TaskState;
import com.rafael.todolist.entity.User;
import com.rafael.todolist.repository.CategoryRepository;
import com.rafael.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CategoryRepository categoryRepo;

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
            model.addAttribute("tasks", user.getTasks());
            model.addAttribute("title", "Accueil - TDL");
        }
        //non connected user
        else{
            //create fake user
            User fakeUser = new User();
            fakeUser.setUsername("Invité");

            //create fake tasks
            Category rangement = categoryRepo.findById(1L).orElseThrow();
            Category cuisine = categoryRepo.findById(2L).orElseThrow();
            Category travaux = categoryRepo.findById(3L).orElseThrow();

            List<Task> fakeTasks = List.of(

                    new Task("Ranger la chambre",
                            "Organiser les vêtements et nettoyer le sol",
                            LocalDateTime.now().plusDays(1),
                            TaskState.TO_DO,
                            rangement),

                    new Task("Nettoyer le bureau",
                            "Enlever la poussière et trier les papiers",
                            LocalDateTime.now().plusDays(2),
                            TaskState.IN_PROGRESS,
                            rangement),

                    new Task("Préparer le dîner",
                            "Faire des pâtes avec sauce tomate",
                            LocalDateTime.now().plusHours(5),
                            TaskState.TO_DO,
                            cuisine),

                    new Task("Faire les courses",
                            "Acheter légumes, viande et lait",
                            LocalDateTime.now().plusDays(1),
                            TaskState.DONE,
                            cuisine),

                    new Task("Peindre le mur",
                            "Repeindre le salon en blanc",
                            LocalDateTime.now().plusDays(3),
                            TaskState.TO_DO,
                            travaux),

                    new Task("Réparer la porte",
                            "Changer la poignée cassée",
                            LocalDateTime.now().plusDays(4),
                            TaskState.IN_PROGRESS,
                            travaux)
            );
            //todo get rid of it
            model.addAttribute("user", fakeUser);
            model.addAttribute("tasks", fakeTasks);
            model.addAttribute("title", "Accueil - TDL - Deconnecté");
        }
        model.addAttribute("isLogged",
                auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)
        );
        return "index";
    }
}