package dao;

import entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

import java.util.List;

/**
 * Created by eriol4ik on 06/02/2017.
 */
public class DAOImpl implements DAO {
    private SessionFactory factory;

    public DAOImpl() {
        factory = HibernateSessionFactory.getSessionFactory();
    }

    @Override
    public <T> Long create(T entity) {
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
    public <T> T read(Class<T> type, Long id) {
        Session session = factory.openSession();
        try {
            return (T) session.get(type, id);
        } finally {
            session.close();
        }
    }

    @Override
    public <T> Boolean update(T entity) {
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
    public <T> Boolean delete(T entity) {
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
    public <T> List<T> findAll(Class<T> type) {
        return (List<T>) factory.openSession().createCriteria(type).list();
    }
}
