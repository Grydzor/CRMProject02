package web.dao;

import web.entity.UserSession;

public interface UserSessionDAO extends DAO<UserSession> {
    void createOrUpdate(UserSession session);
}
