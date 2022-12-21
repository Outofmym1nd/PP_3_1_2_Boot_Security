package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {


    void saveRole(Role role);

    Set<Role> getDefaultRole(long id);

    Role getRoleById(long id);

    Set<Role> getAllRolesWithoutFirst();

    Set<Role> getRoleByName(Set<String> name);
}
