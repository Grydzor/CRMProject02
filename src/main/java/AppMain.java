import entity.User;
import entity.UserSession;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import service.UserService;
import service.UserSessionService;
import util.ApplicationContextFactory;
import util.StageFactory;

public class AppMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageFactory.genericWindow("/view/loading.fxml", "CRM", -1L);
    }
}
