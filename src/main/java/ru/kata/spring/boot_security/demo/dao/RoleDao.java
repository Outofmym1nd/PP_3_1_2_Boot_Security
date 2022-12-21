package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

@Repository
public interface RoleDao {


    void saveRole(Role role);

    Set<Role> getDefaultRole(long id);

    Role getRoleById(long id);

    Set<Role> getAllRolesWithoutFirst();

    Set<Role> getRoleByName(Set<String> name);
}
