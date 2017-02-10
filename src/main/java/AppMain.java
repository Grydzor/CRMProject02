import javafx.application.Application;
import javafx.stage.Stage;
import util.GraphicsLoader;

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphicsLoader.newWindowGeneric("/view/login_panel.fxml", "CRM", false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
