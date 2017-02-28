package dao;

import entity.UserSession;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userSessionDAO")
public class UserSessionDAOImpl extends DAOImpl<UserSession> implements UserSessionDAO {
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected UserSessionDAOImpl() {}

    @Override
    public void createOrUpdate(UserSession userSession) {
        factory.getCurrentSession().saveOrUpdate(userSession);
    }
}
