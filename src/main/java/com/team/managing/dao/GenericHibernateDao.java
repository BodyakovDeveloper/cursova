package com.team.managing.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@EnableTransactionManagement
@Transactional
public abstract class GenericHibernateDao<E> implements Dao<E> {

    private final SessionFactory sessionFactory;

    protected GenericHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(E entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(E entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public void remove(E entity) {
        sessionFactory.getCurrentSession().remove(entity);
    }
}