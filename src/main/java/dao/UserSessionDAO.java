package dao;

import entity.UserSession;

/**
 * Created by eriol4ik on 19.02.2017.
 */
public interface UserSessionDAO extends DAO<UserSession> {
    void createOrUpdate(UserSession session);
}
