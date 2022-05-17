package com.example.rest.controller;

import com.example.rest.entity.UserEntity;
import com.example.rest.model.User;
import com.example.rest.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAll(Model model) {
        val users = User.toModel(userService.getAll());
        model.addAttribute("users", users);
        return "home";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    Object add(UserEntity userEntity, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.addUser(userEntity);
            model.addAttribute("message", "Пользователь добавлен");
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorTitle", "Пользователь не добавлен");
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }


}
