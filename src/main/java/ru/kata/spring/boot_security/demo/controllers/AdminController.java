package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidation;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidation validation;

    @Autowired
    public AdminController(UserService userService, UserValidation validation) {
        this.userService = userService;
        this.validation = validation;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.getAll());
        return "admin";
    }

    @Transactional
    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        userService.delete(id);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("user") User user,
                         BindingResult bindingResult, @RequestParam("id") int id) {
        validation.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "/edit";
        userService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @GetMapping("/adduser")
    public String newUser(@ModelAttribute("user") User user) {
        return "adduser";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @Valid BindingResult bindingResult) {
        validation.validate(user, bindingResult);
        if (bindingResult.hasErrors()) return "adduser";
        userService.save(user);
        return "redirect:/admin";
    }
}
