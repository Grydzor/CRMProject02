package dao;

import entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private SessionFactory factory = HibernateSessionFactory.getSessionFactory();

    @Override
    public Long create(User user) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Long id = (Long) session.save(user);
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
    public User read(Long id) {
        Session session = factory.openSession();
        try {
            return (User) session.get(User.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean update(User user) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.update(user);
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
    public Boolean delete(User user) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.delete(user);
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
    public List<User> findAll() {
        return factory.openSession().createCriteria(User.class).list();
    }
}
