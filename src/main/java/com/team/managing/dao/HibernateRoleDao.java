package com.team.managing.dao;

import com.team.managing.entity.RoleEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class HibernateRoleDao extends GenericHibernateDao<RoleEntity> implements RoleDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateRoleDao(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<RoleEntity> findAllRoles() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(RoleEntity.class);
        return criteria.list();
    }

    public RoleEntity getRoleByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);

        Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));
        Query<RoleEntity> query = session.createQuery(criteriaQuery);

        return query.getSingleResult();
    }
}