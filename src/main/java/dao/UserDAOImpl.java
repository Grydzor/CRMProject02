package dao;

import entity.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

    @Override
    @SuppressWarnings("unchecked")
    public User find(String login) {
        Session session = factory.openSession();
        try {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("login", login));
            List<User> users = criteria.list();
            return users.get(0);
        } finally {
            session.close();
        }
    }
}
