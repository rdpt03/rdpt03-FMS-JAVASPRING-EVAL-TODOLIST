package com.rafael.todolist.controller;

import com.rafael.todolist.entity.Category;
import com.rafael.todolist.entity.Task;
import com.rafael.todolist.entity.TaskState;
import com.rafael.todolist.entity.User;
import com.rafael.todolist.repository.CategoryRepository;
import com.rafael.todolist.repository.TaskRepository;
import com.rafael.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller responsible for handling the home page and task state updates.
 * It manages both authenticated and non-authenticated users.
 */
@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private TaskRepository taskRepo;

    /**
     * Displays the home page.
     * <p>
     * If the user is authenticated, their tasks are loaded.
     * Otherwise, a fake user with demo tasks is displayed.
     *
     * @param auth  the current authentication object
     * @param model the Spring MVC model
     * @return the index view name
     */
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
            model.addAttribute("title", "Accueil - TDA");
        }
        //non connected user
        else{
            //create fake user
            User fakeUser = new User();
            fakeUser.setUsername("Invité");

            //generate fake tasks
            List<Task> fakeTasks = generateFakeTasks();

            //todo get rid of it
            model.addAttribute("user", fakeUser);
            model.addAttribute("tasks", fakeTasks);
            model.addAttribute("title", "Accueil - TDA - Deconnecté");
        }
        model.addAttribute("isLogged",
                auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)
        );
        return "index";
    }


    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    /**
     * Updates the state of a task.
     * <p>
     * Ensures that the task belongs to the authenticated user
     * before applying the update.
     *
     * @param id    the task ID
     * @param state the new task state
     * @param auth  the current authenticated user
     * @return "OK" if the update is successful
     * @throws RuntimeException if the task is not found or access is denied
     */
    @PostMapping("/task/{id}/state")
    @ResponseBody
    public String updateTaskState(@PathVariable Long id, @RequestParam TaskState state, Authentication auth) {
        //get logged user
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();

        //get task
        Task task = taskRepo.findById(id)
                .filter(t -> t.getUser().getId().equals(user.getId())) // garante que pertence ao user
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée ou accès refusé"));

        //update status
        task.setState(state);
        taskRepo.save(task);

        return "OK";
    }

    /**
     * Generates a list of fake tasks for non-authenticated users.
     *
     * @return a list of demo tasks
     */
    private List<Task> generateFakeTasks(){
        //create fake tasks
        Category rangement = categoryRepo.findById(1L).orElseThrow();
        Category cuisine = categoryRepo.findById(2L).orElseThrow();
        Category travaux = categoryRepo.findById(3L).orElseThrow();

        return List.of(

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
    }
}