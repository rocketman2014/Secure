package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class UserValidation implements Validator {
    private final UserService userDevServ;

    @Autowired
    public UserValidation(UserService userDevServ) {
        this.userDevServ = userDevServ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        User userExist;
        try {
            userExist =  userDevServ.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        if (user.getId() != null && userExist.getId().equals(user.getId()))
            return;
        errors.rejectValue("username", "",
                "Человек с таким именем пользователя уже существует");
    }
}

