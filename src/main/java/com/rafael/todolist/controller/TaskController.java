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

import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private TaskRepository taskRepo;

    //---------------------REGISTER--------------------------
    @PostMapping("/task/create")
    public String createTaskSubmit(@ModelAttribute Task task, Authentication auth) {
        //get atual user
        User user = userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Erreur user non trouvé!"));

        // associate user totask
        task.setUser(user);

        //save it
        taskRepo.save(task);

        //redirect
        return "redirect:/index";
    }

    @GetMapping("/task/create")
    public String creaTaskRender(Model model){
        //get available categs
        List<Category> categs = categoryRepo.findAll();

        //send to front
        model.addAttribute("categories",categs);

        return "/task/form";
    }
}
