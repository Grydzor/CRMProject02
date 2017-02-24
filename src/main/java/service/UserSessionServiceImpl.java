package service;

import dao.UserSessionDAO;
import dao.UserSessionDAOImpl;
import entity.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userSessionService")
public class UserSessionServiceImpl extends UserSessionDAOImpl implements UserSessionService {
    @Autowired
    @Qualifier("userSessionDAO")
    private UserSessionDAO userSessionDAO;

    private UserSessionServiceImpl() {
        super();
    }


    @Override
    public void createOrUpdate(UserSession session) {
        userSessionDAO.createOrUpdate(session);
    }
}
