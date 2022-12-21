package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class RoleDaoImpl implements RoleDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Set<Role> getDefaultRole(long id) {
        return Stream.of(entityManager.find(Role.class, id)).collect(Collectors.toSet());
    }

    @Override
    public Role getRoleById(long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Set<Role> getAllRolesWithoutFirst() {
        List<Role> listRole = entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        listRole.remove(0);
        return new HashSet<>(listRole);
    }

    @Override
    public Set<Role> getRoleByName(Set<String> name) {
        List<Role> listRole = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name)
                .getResultList();
        return new HashSet<>(listRole);
    }
}
