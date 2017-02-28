import entity.UserSession;
import javafx.application.Application;
import javafx.stage.Stage;
import service.UserSessionService;
import util.ApplicationContextFactory;
import util.StageFactory;

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserSession session = ApplicationContextFactory
                .getApplicationContext()
                .getBean(UserSessionService.class).restoreSession();
        // todo add log-in with UserSession
        StageFactory.genericWindow("/view/login_panel_two.fxml", "CRM", -1L);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
