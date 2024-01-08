package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RegisterService;
import ru.kata.spring.boot_security.demo.service.UsersDetailService;
import ru.kata.spring.boot_security.demo.util.UserValidation;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class RegistrationController {

    private final RegisterService registerService;
    private final UserValidation userValidation;

    @Autowired
    public RegistrationController( RegisterService registerService, UserValidation userValidation) {
        this.registerService = registerService;
        this.userValidation = userValidation;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult) {
        userValidation.validate(user,bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        registerService.register(user);
        return "redirect:/user";
    }
}

