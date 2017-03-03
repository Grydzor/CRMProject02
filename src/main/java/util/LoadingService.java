package util;

import entity.UserSession;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import service.UserSessionService;

/**
 * Created by eriol4ik on 03.03.2017.
 */
public class LoadingService extends Service<UserSession> {
    @Override
    protected Task<UserSession> createTask() {
        return new Task<UserSession>() {
            @Override
            protected UserSession call() throws Exception {
                return ApplicationContextFactory
                        .getApplicationContext()
                        .getBean(UserSessionService.class).restoreSession();
            }
        };
    }
}
