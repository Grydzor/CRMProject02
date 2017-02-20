package util;

import controller.modal.ParameterSettable;
import entity.UserSession;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.UserSessionServiceImpl;

import java.io.IOException;

public class StageFactory {
    private static Stage stageWindow;
    private static Stage stageModal;

    static {
        stageWindow = new Stage();
        stageModal = new Stage();
        stageModal.initModality(Modality.APPLICATION_MODAL);
    }


    public static <T> T genericWindow(String resource, String title, Long userId) {
        //UserSession userSession = UserSession.writeToResource(userId);

        FXMLLoader loader = new FXMLLoader(StageFactory.class.getResource(resource));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Проблема в пути к FXML");
            return null;
        }
        stageWindow.setOnCloseRequest((event) ->
                HibernateSessionFactory.getSessionFactory().close());
        stageWindow.setTitle(title);

        stageWindow.setResizable(true);
        stageWindow.setMinHeight(580);
        stageWindow.setMinWidth(980);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/view/styles/default.css");
        stageWindow.setScene(scene);

        T controller = loader.getController();

        stageWindow.show();

        return controller;
    }

    public static <T> T genericWindow(String resource, String title, Long userId, String style) {
        //UserSession userSession = UserSession.writeToResource(userId);

        FXMLLoader loader = new FXMLLoader(StageFactory.class.getResource(resource));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Проблема в пути к FXML");
            return null;
        }
        stageWindow.setOnCloseRequest((event) ->
                HibernateSessionFactory.getSessionFactory().close());
        stageWindow.setTitle(title);

        stageWindow.setResizable(true);
        stageWindow.setMinHeight(580);
        stageWindow.setMinWidth(980);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(style);
        stageWindow.setScene(scene);

        T controller = loader.getController();

        stageWindow.show();

        return controller;
    }

    public static <T extends ParameterSettable<T2, T3>, T2, T3> T3 genericModal(String resource, String title, T2 parameter) {
        FXMLLoader loader = new FXMLLoader(StageFactory.class.getResource(resource));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Проблема в пути к FXML");
            return null;
        }

        stageModal.setResizable(false);

        Scene scene = new Scene(root);
        stageModal.setScene(scene);
        stageModal.setTitle(title);

        T controller = loader.getController();

        if (parameter != null) {
            controller.setParameter(parameter);
        }

        stageModal.showAndWait();

        return controller.getResult();
    }

    // Передать в метод любой элемент который находится в окне, которое нужно закрыть.
    public static void backToLogInWindow() {
        stageWindow.close();
        StageFactory.genericWindow("/view/login_panel.fxml", "Login panel", null);
    }

    public static void closeLogInWindow(){
        stageWindow.close();
    }

    public static void closeModal() {
        stageModal.close();
    }

}
