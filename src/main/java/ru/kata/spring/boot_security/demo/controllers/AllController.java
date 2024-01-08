package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersDetailService;

import java.util.List;

@Controller
public class AllController {
    private final UsersDetailService userService;
    @Autowired
    public AllController(UsersDetailService userService) {
        this.userService = userService;
    }
    @GetMapping("/user")
    public String show(Model model, Authentication authentication) {
       UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        User curretUser = (User) userService.loadUserByUsername(userDetails.getUsername());
        model.addAttribute("user", curretUser);
        return "user";
    }

}

