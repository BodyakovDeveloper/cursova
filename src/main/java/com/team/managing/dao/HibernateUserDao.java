package com.team.managing.dao;

import com.team.managing.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Component
@EnableTransactionManagement
public class HibernateUserDao extends GenericHibernateDao<UserEntity> implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<UserEntity> findAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From UserEntity").list();
    }

    public UserEntity findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserEntity where login=:login");
        query.setParameter("login", login);
        return (UserEntity) query.uniqueResult();
    }

    public UserEntity findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserEntity where email=:email");
        query.setParameter("email", email);
        return (UserEntity) query.uniqueResult();
    }

    public boolean isUserExists(String login, String email) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserEntity where login = :login or email = :email");
        query.setParameter("login", login);
        query.setParameter("email", email);

        return query.uniqueResult() != null;
    }
}