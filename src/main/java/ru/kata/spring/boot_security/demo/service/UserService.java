package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAOImp;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserDAOImp userDAO;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;
    @Autowired
    public UserService(UserDAOImp userDAO, BCryptPasswordEncoder encoder, RoleService roleService) {
        this.userDAO = userDAO;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    public List<User> getAll(){
        return userDAO.getAll();
    }
    public User findById(int id){
        Optional<User> user = Optional.ofNullable(userDAO.findById(id));
        return user.orElse(new User());
    }
    @Transactional
    public void save(User user){
        operationsWithRoles(user);
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.save(user);
    }
    @Transactional
    public void delete(int id) {
        userDAO.delete(id);
    }
    @Transactional
    public void update(int id,User user) {
        user.setId(id);
        operationsWithRoles(user);
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDAO.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        return user.get();
    }
    public void operationsWithRoles(User user) {
        Set<Role> roles = new HashSet<>();
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            roles.add(roleService.findById(2));
        } else {
            roles.add(roleService.findById(1));
        }
        user.setRoles(roles);
    }
}
