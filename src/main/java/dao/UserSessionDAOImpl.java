package dao;

import entity.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userSessionDAO")
public class UserSessionDAOImpl extends DAOImpl<UserSession> implements UserSessionDAO {

    @Autowired
    protected UserSessionDAOImpl() {
        super(UserSession.class);
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
