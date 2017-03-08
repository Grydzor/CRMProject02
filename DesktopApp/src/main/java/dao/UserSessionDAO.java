package dao;

import entity.UserSession;

public interface UserSessionDAO extends DAO<UserSession> {
    void createOrUpdate(UserSession session);
}
