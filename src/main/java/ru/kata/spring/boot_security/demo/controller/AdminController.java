package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("listUser", userService.getAllUsers());
        model.addAttribute("setRoles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") User user) {
        setRoles(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/users/{id}")
    public String editUser(@PathVariable(value = "id") Integer id, @ModelAttribute("user") User user) {
        if (!user.getRoles().isEmpty()) {
            setRoles(user);
        } else {
            Set<Role> roles = userService.getUser(id).getRoles();
            user.setRoles(roles);
        }
        userService.editUser(user);
        return "redirect:/admin";
    }

    private void setRoles(@ModelAttribute("user") User user) {
        Set<Role> roles = roleService.getDefaultRole(1L);
        Set<String> roleNames = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());
        Set<Role> additionalRoles = roleService.getRoleByName(roleNames);
        roles.addAll(additionalRoles);
        user.setRoles(roles);
    }

    @DeleteMapping("/users/{id}")
    public String removeUserById(@PathVariable(value = "id") Integer id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}