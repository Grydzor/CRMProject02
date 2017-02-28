package service;

import entity.UserSession;

/**
 * Created by eriol4ik on 19.02.2017.
 */
public interface UserSessionService extends Service<UserSession> {
    void createOrUpdate(UserSession session);

    UserSession restoreSession();
    UserSession writeToResource(Long userId);
}
