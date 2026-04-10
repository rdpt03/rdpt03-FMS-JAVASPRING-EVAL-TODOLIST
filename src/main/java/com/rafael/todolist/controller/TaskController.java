package com.rafael.todolist.controller;

import com.rafael.todolist.entity.Category;
import com.rafael.todolist.entity.Task;
import com.rafael.todolist.entity.User;
import com.rafael.todolist.repository.CategoryRepository;
import com.rafael.todolist.repository.TaskRepository;
import com.rafael.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller responsible for task creation.
 * Handles rendering the task form and submitting new tasks.
 */
@Controller
public class TaskController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private TaskRepository taskRepo;

    /**
     * Handles the submission of a new task.
     * <p>
     * Associates the task with the currently authenticated user
     * and saves it in the database.
     *
     * @param task the task object populated from the form
     * @param auth the current authenticated user
     * @return the task creation form view
     * @throws UsernameNotFoundException if the user is not found
     */
    @PostMapping("/task/create")
    public String createTaskSubmit(@ModelAttribute Task task, @RequestParam Long categoryId, Authentication auth) {
        //get atual user
        User user = userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Erreur user non trouvé!"));

        // associate user to task
        task.setUser(user);

        //associate category to task
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        task.setCategory(category);

        //save it
        taskRepo.save(task);

        //redirect
        return "redirect:/index";
    }

    /**
     * Displays the task creation form.
     * <p>
     * Loads all available categories to populate the form.
     *
     * @param model the Spring MVC model
     * @return the task creation form view
     */
    @GetMapping("/task/create")
    public String creaTaskRender(Model model){
        //get available categs
        List<Category> categs = categoryRepo.findAll();

        //send to front
        model.addAttribute("categories",categs);

        return "/task/form";
    }
}
