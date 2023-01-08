package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleDao {


    void saveRole(Role role);

    Set<Role> getDefaultRole(long id);

    Role getRoleById(long id);

    Set<Role> getRoles();

    Set<Role> getRoleByName(Set<String> name);
}
