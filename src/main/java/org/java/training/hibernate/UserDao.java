package org.java.training.hibernate;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Repository
public class UserDao {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Collection<User> findAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createCriteria(User.class).list();
        }
    }

    public User findUserUsingCriteria(String userName) {
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("fullName", userName));

            User user = (User) criteria.uniqueResult();
            return user;
        }
    }

    public User findUser(String userName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("select u from User u where u.fullName=:userName");
            query.setString("userName", userName);

            return (User) query.uniqueResult();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,value = "t1")
    public void save(User user) {
        user.setPasswordExpirityDate(getPasswordExpiryDate());

        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }
    }

    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete("User", user);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
        }
    }

    private String getPasswordExpiryDate() {
        LocalDate expiryDate = LocalDate.now().plusDays(10);
        return expiryDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
    }
}
