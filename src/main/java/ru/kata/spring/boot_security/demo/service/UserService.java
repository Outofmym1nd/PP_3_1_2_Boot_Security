package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User getUser(long id);

    User findUserByEmail(String email);

    List<User> getAllUsers();

    void saveUser(User user);

    void editUser(User user);

    void removeUserById(long id);

    void addRole(User user, String role);
}
