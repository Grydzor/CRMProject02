package dao;

import entity.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    @SuppressWarnings("unchecked")
    public User find(String login) {
        Session session = factory.openSession();
        try {
            List<User> users = session
                    .createCriteria(User.class)
                    .add(Restrictions.eq("login", login))
                    .list();

            return !users.isEmpty() ? users.get(0) : null;
        } finally {
            session.close();
        }
    }
}
