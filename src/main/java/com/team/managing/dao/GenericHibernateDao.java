package com.team.managing.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public abstract class GenericHibernateDao<E> implements Dao<E> {

    private final SessionFactory sessionFactory;

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