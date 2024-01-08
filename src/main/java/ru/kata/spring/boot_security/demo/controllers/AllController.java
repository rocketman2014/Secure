package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersDetailService;

@Controller
public class AllController {
    private final UsersDetailService userService;
    @Autowired
    public AllController(UsersDetailService userService) {
        this.userService = userService;
    }
    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUser());
        return "admin";
    }
    @GetMapping("/user")
    public String show(Model model, Authentication authentication) {
       UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        User curretUser = (User) userService.loadUserByUsername(userDetails.getUsername());
        model.addAttribute("user", curretUser);
        return "user";
    }
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @PostMapping("/{id}")
    public String update(@ModelAttribute("update") User user,
                         @RequestParam("id") long id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
}

