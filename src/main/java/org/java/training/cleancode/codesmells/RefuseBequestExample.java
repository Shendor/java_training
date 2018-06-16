package org.java.training.cleancode.codesmells;

public class RefuseBequestExample {

    public void updateRolesForUser(int userId, String roles) {
        UserRolesDao userRolesDao = new UserRolesDao();

        User user = userRolesDao.getById(userId);
        user.setRoles(roles);
        userRolesDao.update(user);
    }

    class User {

        private int id;
        private String roles;

        public User(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }
    }

    class AbstractDao<T> {

        void insert(T entity) {
        }

        void update(T entity) {
        }

        void delete(T entity) {
        }

        void getAll(T entity) {
        }

        T getById(int entityId) {
            return null;
        }

    }

    class UserRolesDao extends AbstractDao<User> {

    }
    
    

}
