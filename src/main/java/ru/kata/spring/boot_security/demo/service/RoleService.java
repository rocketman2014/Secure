package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAOImp;
import ru.kata.spring.boot_security.demo.model.Role;

@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleDAOImp roleDAO;
    @Autowired
    public RoleService(RoleDAOImp roleDAO) {
        this.roleDAO = roleDAO;
    }
    @Transactional
    public void save(Role role) {
        roleDAO.save(role);
    }
    public Role findById(int id) {
        return roleDAO.findById(id);
    }
}
