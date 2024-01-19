package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleDAO {
    void save(Role role);

    Role findById(int id);
}