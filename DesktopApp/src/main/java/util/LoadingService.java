package util;

import entity.UserSession;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import service.UserSessionService;

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
