package dao;

import entity.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

/**
 * Created by eriol4ik on 19.02.2017.
 */
public class UserSessionDAOImpl extends DAOImpl<UserSession> implements UserSessionDAO {
    private SessionFactory factory;
    private static UserSessionDAOImpl singleton;

    private UserSessionDAOImpl() {
        super(UserSession.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }

    public static UserSessionDAOImpl getInstance() {
        if (singleton == null) singleton = new UserSessionDAOImpl();
        return singleton;
    }

    @Override
    public void createOrUpdate(UserSession userSession) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.save(userSession);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
