package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DAOImpl<T> implements DAO<T> {

    @Autowired
    protected SessionFactory factory;
    private Class<T> entityClass;

    public DAOImpl(Class<T> entityClass) {
        //factory = HibernateSessionFactory.getSessionFactory();
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
    public T read(Long id) {
        Session session = factory.openSession();
        try {
            return session.get(entityClass, id);
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
    public List<T> findAll() {
        try(Session session = factory.openSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName() + " t", entityClass).list();
        }
    }
}
