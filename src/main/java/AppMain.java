import javafx.application.Application;
import javafx.stage.Stage;
import util.StageFactory;

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageFactory.genericWindow("/view/login_panel_two.fxml", "CRM", -1L, "/view/styles/light_theme.css");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
