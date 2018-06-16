package org.java.training.jdbc;

public class JdbcTraining {

    public static void main(String[] args) throws ClassNotFoundException {
        UserDao userDao = new UserDao();

        User user = new User(1000, "test3");
        if (userDao.findUserById(user.getId()).isPresent()) {
            userDao.deleteUser(user.getId());
        }

        userDao.insertUser(user);
        userDao.findAllUsers().forEach(System.out::println);
    }
}
