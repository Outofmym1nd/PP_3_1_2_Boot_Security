package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void saveUserRole(User user, String role) {
        entityManager.persist(user);
    }

    @Override
    public void editUser(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Override
    public void removeUserById(long id) {
        entityManager.remove(getUser(id));
    }

    public void addRole(User user, String role) {
        Role roleUser = new Role(1L, "ROLE_USER");
        List<Role> listRoles = new ArrayList<>();
        listRoles.add(roleUser);
        if (role.contains("ROLE_ADMIN")) {
            Role roleAdmin = new Role(2L, "ROLE_ADMIN");
            listRoles.add(roleAdmin);
        }
        user.setRoles(listRoles);
    }
}
