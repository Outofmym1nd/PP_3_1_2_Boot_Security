package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {


    User getUser(long id);

    Optional<User> findUserByEmail(String email);

    List<User> getAllUsers();

    void saveUser(User user);

    void editUser(User user);

    void removeUserById(long id);

    void saveUserRole(User user, String role);

    void addRole(User user, String role);
}