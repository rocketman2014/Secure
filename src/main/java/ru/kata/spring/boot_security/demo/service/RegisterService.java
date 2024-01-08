package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Autowired
    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    @Transactional
    public void register (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null || user.getRole().isEmpty()) {
            Role userRole = roleRepository.findByRole("ROLE_USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setRole("ROLE_USER");
                roleRepository.save(userRole);
            }
            user.setRole(new HashSet<>(List.of(userRole)));
        }
        userRepository.save(user);
    }
}
