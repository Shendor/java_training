package org.java.training.jdbc;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class UserDao {

    private static final String FETCH_USERS = "SELECT * FROM T_USER";
    private static final String DELETE_USERS = "DELETE FROM T_USER WHERE USER_ID=?";
    private static final String INSERT_USER = "INSERT INTO T_USER(USER_ID, USER_IDENTIFICATION, PASSWORD, FULL_NAME, " +
            "EMAIL_ID, CONTACT_PHONE_NUMBER, PASSWORD_EXPIRY_DATE, USER_STATUS, PASSWORD_SALT) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@10.105.10.41:1521/paypdsdv.test.vocalink.co.uk";
    private static final String USER = "RAT_PAYPDS";
    private static final String PASS = "RAT_PAYPDS123-";

    public UserDao() throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
    }

    public void insertUser(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
                statement.setLong(1, 1000L);
                statement.setString(2, user.getFullName());
                statement.setString(3, "pswd");
                statement.setString(4, user.getFullName());
                statement.setString(5, "mail@mail.com");
                statement.setString(6, "12345");
                statement.setDate(7, Date.valueOf(LocalDate.now()));
                statement.setString(8, "ACTIVE");
                statement.setString(9, "salt");

                statement.executeUpdate();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void deleteUser(long userId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_USERS)) {
                statement.setLong(1, userId);
                statement.executeUpdate();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public Optional<User> findUserById(long userId) {
        return findAllUsers().stream().filter(user -> user.getId() == userId).findFirst();
    }

    public Collection<User> findAllUsers() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            try (Statement statement = connection.createStatement()) {
                return executeQueryToFetchAllUsers(statement);
            }
        } catch (SQLException se) {
            return Collections.emptyList();
        }
    }

    private Collection<User> executeQueryToFetchAllUsers(Statement statement) throws SQLException {
        Collection<User> users = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery(FETCH_USERS)) {
            while (resultSet.next()) {
                users.add(mapToUserFromResultSet(resultSet));
            }
        }
        return users;
    }

    private User mapToUserFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("USER_ID");
        String fullName = resultSet.getString("FULL_NAME");
        return new User(id, fullName);
    }
}
