package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class InitData {

    private final RoleService roleService;
    private final UserService userService;

    public InitData(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void initRolesAndUsers() {
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleService.saveRole(roleUser);
        roleService.saveRole(roleAdmin);
        User user = new User("1", "Ivan", "Ivanov", (byte) 20, "1", Set.of(roleUser));
        User admin = new User("2", "Petr", "Petrov", (byte) 30, "2", Set.of(roleAdmin, roleUser));
        userService.saveUser(user);
        userService.saveUser(admin);
    }
}
