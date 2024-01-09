package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersDetailService;
import ru.kata.spring.boot_security.demo.util.UserValidation;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UsersDetailService userService;
    private final UserValidation validation;

    @Autowired
    public AdminController(UsersDetailService userService, UserValidation validation) {
        this.userService = userService;
        this.validation = validation;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUser());
        return "admin";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/edit{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@Valid @ModelAttribute("user") User user,
                         BindingResult bindingResult,
                         @RequestParam("id") long id) {
        validation.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/edit";
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(Model model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.findByUserId(id));
        return "edit";
    }

    @GetMapping("/adduser")
    @PreAuthorize("hasRole('ADMIN')")
    public String newUser(@ModelAttribute("user") User user) {
        return "adduser";
    }

    @PostMapping("/adduser")
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@Valid @ModelAttribute("user") User user,
                         BindingResult bindingResult) {
        validation.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/adduser";
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
