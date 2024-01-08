package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersDetailService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UsersDetailService userService;

    @Autowired
    public AdminController(UsersDetailService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUser());
        return "admin";
    }

    @GetMapping("/show")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminShow(@RequestParam("id") long id, Model model){
        model.addAttribute("user", userService.findByUserId(id));
        return "user";
    }
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@ModelAttribute("update") User user,
                         @RequestParam("id") long id) {
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
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@ModelAttribute("user")  User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
