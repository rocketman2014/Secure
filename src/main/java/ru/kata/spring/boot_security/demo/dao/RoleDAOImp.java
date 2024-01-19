package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;

@Repository
public class RoleDAOImp implements RoleDAO {
    private final EntityManager entityManager;

    @Autowired
    public RoleDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Role role) {
        if (!entityManager.contains(role))
            role = entityManager.merge(role);
        entityManager.persist(role);
    }

    @Override
    public Role findById(int id) {
        return entityManager.find(Role.class, id);
    }

}

