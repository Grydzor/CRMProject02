import javafx.application.Application;
import javafx.stage.Stage;
import util.StageFactory;

public class AppMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageFactory.loadWindow("/view/loading.fxml", "CRM", -1L);
    }
}