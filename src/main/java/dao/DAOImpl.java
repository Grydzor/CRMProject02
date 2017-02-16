package dao;

import entity.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import util.HibernateSessionFactory;

import java.util.List;

/**
 * Created by eriol4ik on 06/02/2017.
 */
public class DAOImpl<T> implements DAO<T> {
    private SessionFactory factory;
    private Class<T> entityClass;

    public DAOImpl(Class<T> entityClass) {
        factory = HibernateSessionFactory.getSessionFactory();
        this.entityClass = entityClass;
    }

    @Override
    public Long create(T entity) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Long id = (Long) session.save(entity);
            session.getTransaction().commit();
            return id;
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read(Long id) {
        Session session = factory.openSession();
        try {
            return (T) session.get(entityClass, id);
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean update(T entity) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean delete(T entity) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return (List<T>) factory.openSession().createCriteria(entityClass).list();
    }
}