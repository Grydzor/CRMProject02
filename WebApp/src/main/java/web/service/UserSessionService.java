package web.service;

import web.entity.UserSession;

public interface UserSessionService extends Service<UserSession> {
    void createOrUpdate(UserSession session);

    UserSession restoreSession();
    UserSession writeToResource(Long userId);
}
