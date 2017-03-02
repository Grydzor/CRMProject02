import entity.User;
import entity.UserSession;
import javafx.application.Application;
import javafx.stage.Stage;
import service.UserService;
import service.UserSessionService;
import util.ApplicationContextFactory;
import util.StageFactory;

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserSession session = ApplicationContextFactory
                .getApplicationContext()
                .getBean(UserSessionService.class).restoreSession();
        if (session == null) {
            StageFactory.genericWindow("/view/login_panel.fxml", "CRM", -1L);
        } else {
            User user = ApplicationContextFactory.getApplicationContext()
                    .getBean(UserService.class).read(session.getUserId());
            switch (user.getEmployee().getPosition()) {
                case ADMIN:
                    StageFactory.genericWindow("/view/admin_panel.fxml", "Administration", session.getUserId());
                    break;
                case MANAGER:
                    StageFactory.genericWindow("/view/manager_panel.fxml", "Management", session.getUserId());
                    break;
                case CASHIER:
                    StageFactory.genericWindow("/view/cashier_panel.fxml", "Cashier", session.getUserId());
                    break;
                case STOREKEEPER:
                    StageFactory.genericWindow("/view/storage_panel_two.fxml", "Storage", session.getUserId());
                    break;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
