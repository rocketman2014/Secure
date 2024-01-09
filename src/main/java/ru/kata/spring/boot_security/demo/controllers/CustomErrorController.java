package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController {
    @RequestMapping("/403")
    public String error403() {
        return "error/error403";
    }
}
