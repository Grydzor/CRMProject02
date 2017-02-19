package service;

import dao.UserSessionDAO;
import dao.UserSessionDAOImpl;
import entity.UserSession;

/**
 * Created by eriol4ik on 19.02.2017.
 */
public class UserSessionServiceImpl extends ServiceImpl<UserSession> implements UserSessionService {
    private UserSessionDAO userSessionDAO;
    private static UserSessionServiceImpl singleton;

    private UserSessionServiceImpl() {
        super(UserSession.class);
        userSessionDAO = UserSessionDAOImpl.getInstance();
    }

    public static UserSessionServiceImpl getInstance() {
        if (singleton == null) singleton = new UserSessionServiceImpl();
        return singleton;
    }

    @Override
    public void createOrUpdate(UserSession session) {
        userSessionDAO.createOrUpdate(session);
    }
}
