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

//    @GetMapping()
//    public String getUser(Principal principal, Model model) {
//        User user = userService.findUserByEmail(principal.getName());
//        model.addAttribute("user", user);
//        String roles = user.showRoles();
//        model.addAttribute("listRole", roles);
//        return "admin";
//    }

    @GetMapping()
    public String getAllUsers( Integer id, Principal principal, Model model) {
        User newUser = new User();
        User user = userService.findUserByEmail(principal.getName());
        String roles = user.showRoles();
        model.addAttribute("user", user);
        model.addAttribute("newUser", newUser);
        model.addAttribute("listRole", roles);
        model.addAttribute("listUser", userService.getAllUsers());
        model.addAttribute("setRoles", roleService.getRoles());
//        model.addAttribute("currentUser", userService.getUser(id));
        return "admin";
    }

//    @GetMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("setRoles", roleService.getAllRolesWithoutFirst());
//        return "new";
//    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") User user) {
        setRoles(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("setRoles", roleService.getRoles());
        return "edit";
    }

    @PatchMapping("/users/{id}")
    public String editUser(@ModelAttribute("user") User user) {
        setRoles(user);
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