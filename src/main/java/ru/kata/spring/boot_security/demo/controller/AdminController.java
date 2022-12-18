package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {


    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUser(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        String roles = user.showRoles();
        model.addAttribute("listRole", roles);
        return "user";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("listUser", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        userService.addRole(user, role);
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PatchMapping("/users/{id}")
    public String editUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        userService.addRole(user, role);
        userService.editUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String removeUserById(@PathVariable(value = "id") Integer id) {
        userService.removeUserById(id);
        return "redirect:/admin/users";
    }
}
