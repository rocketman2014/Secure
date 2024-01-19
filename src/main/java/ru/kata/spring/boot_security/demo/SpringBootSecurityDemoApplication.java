package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {
    public static RoleService roleService;

    public SpringBootSecurityDemoApplication(RoleService roleService) {
        this.roleService=roleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
        //для инициализации при Create
//        roleService.save(new Role("ROLE_USER"));
//        roleService.save(new Role("ROLE_ADMIN"));
    }

}
