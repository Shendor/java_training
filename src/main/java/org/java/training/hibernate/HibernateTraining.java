package org.java.training.hibernate;

import org.java.training.hibernate.config.DatabaseConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.Set;

public class HibernateTraining {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DatabaseConfig.class);

        UserDao userDao = applicationContext.getBean(UserDao.class);

        User foundUser = userDao.findUserUsingCriteria("test2");

        System.out.println(foundUser);
        if (foundUser != null) {
            userDao.delete(foundUser);
            printAllUsers(userDao);
        }

        User user = new User();
        user.setFullName("test2");
        user.setEmail("test2@mail");
        user.setPassword("test");
        user.setPasswordSalt("salt");
        user.setUserId("test2");
        user.setUserStatus("ACTIVE");
        user.setContactPhoneNum("12345");

        Set<UserRole> roles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRoleCode("ROLE_OPERATOR");
        roles.add(userRole);
        user.setRoles(roles);

        userDao.save(user);

        foundUser = userDao.findUser("test2");
        System.out.println(foundUser);
        System.out.println(foundUser.getRoles());
    }

    private static void printAllUsers(UserDao userDao) {
        userDao.findAllUsers().forEach(user -> System.out.println(user.getFullName()));
    }
}
