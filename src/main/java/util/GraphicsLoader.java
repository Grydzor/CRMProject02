package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphicsLoader {

    public static void newWindow(String resource, String title) {
        Parent root = null;
        try {
            root = FXMLLoader.load(GraphicsLoader.class.getResource(resource));
        } catch (IOException e) {
            System.out.println("Проблема в пути к FXML");
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void newModalWindow(String resource, String title) {
        Parent root = null;
        try {
            root = FXMLLoader.load(GraphicsLoader.class.getResource(resource));
        } catch (IOException e) {
            System.out.println("Проблема в пути к FXML");
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    // Передать в метод любой элемент который находиться в окне, которое нужно зыкрыть.
    public static void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

}
