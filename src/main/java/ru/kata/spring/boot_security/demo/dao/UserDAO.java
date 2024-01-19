package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> getAll();
    void save(User user);
    User findById(int id);
    void delete(int id);
    Optional<User> findByUsername(String username);
}
