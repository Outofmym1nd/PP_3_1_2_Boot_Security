package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("listUser", userService.getAllUsers());
        model.addAttribute("setRoles", roleService.getRoles());
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        setRoles(user);
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<User> editUser(@PathVariable Integer id, @RequestBody User user) {
        if (!user.getRoles().isEmpty()) {
            setRoles(user);
        } else {
            Set<Role> roles = userService.getUser(id).getRoles();
            user.setRoles(roles);
        }
        userService.editUser(user);
        return ResponseEntity.ok(user);
    }

    private void setRoles(@ModelAttribute("user") User user) {
        Set<Role> roles = roleService.getDefaultRole(1L);
        Set<String> roleNames = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());
        Set<Role> additionalRoles = roleService.getRoleByName(roleNames);
        roles.addAll(additionalRoles);
        user.setRoles(roles);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUserById(@PathVariable Integer id) {
        userService.removeUserById(id);
    }
}