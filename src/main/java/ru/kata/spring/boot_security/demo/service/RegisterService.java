package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

@Service
public class RegisterService {
    private final UsersDetailService usersDetailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UsersDetailService usersDetailService, PasswordEncoder passwordEncoder) {
        this.usersDetailService = usersDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDetailService.saveUser(user);
    }
}
