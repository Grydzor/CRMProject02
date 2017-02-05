package util;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

//todo
public class GraphicsLoader {
    private void loadAndShow(String resource, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle(title);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        Controller controller = loader.getController();
//        controller.setService(service);

        stage.show();
    }
}
