package dao;

import entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

import java.util.List;

public class UserDAOImpl extends DAOImpl<User> implements UserDAO {
    private SessionFactory factory;
    private static UserDAOImpl singleton;

    private UserDAOImpl() {
        super(User.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }

    public static UserDAOImpl getInstance() {
        if (singleton == null) singleton = new UserDAOImpl();
        return singleton;
    }

    @Override
    public User find(String login) {
        Session session = factory.openSession();
        try {

            List<User> users = session
                    .createQuery("FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .list();
            return users != null && !users.isEmpty() ? users.get(0) : null;
        } finally {
            session.close();
        }
    }
}
