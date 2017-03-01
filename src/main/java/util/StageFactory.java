package util;

import controller.modal.ValueSettable;
import entity.UserSession;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.UserSessionService;

import java.io.IOException;

public class StageFactory {
    private static Stage stageWindow;
    private static Stage stageModal;

    static {
        stageWindow = new Stage();
        stageModal = new Stage();
        stageModal.initModality(Modality.APPLICATION_MODAL);

        stageWindow.setResizable(true);
        stageWindow.setMinHeight(540);
        stageWindow.setMinWidth(960);

        stageWindow.getIcons().add(new Image("/view/imgs/little_icon.png"));
        stageWindow.setOnCloseRequest((event) ->
                HibernateSessionFactory.getSessionFactory().close());
    }


    public static <T> T genericWindow(String resource, String title, Long userId) {
        FXMLLoader loader = new FXMLLoader(StageFactory.class.getResource(resource));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Проблема в пути к FXML");
            return null;
        }
        stageWindow.setTitle(title);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/view/styles/light_theme.css");
//        scene.getStylesheets().add("/view/styles/dark_theme.css");
        stageWindow.setScene(scene);

        T controller = loader.getController();

        if (!Long.valueOf(-1L).equals(userId)) {
            ApplicationContextFactory.getApplicationContext()
                    .getBean(UserSessionService.class).writeToResource(userId);
        }

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
        stageWindow.getIcons().add(new Image("/view/imgs/little_icon.png"));

        stageWindow.setOnCloseRequest((event) ->
                HibernateSessionFactory.getSessionFactory().close());
        stageWindow.setTitle(title);


        stageWindow.setResizable(true);
        stageWindow.setMinHeight(540);
        stageWindow.setMinWidth(960);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/view/styles/light_theme.css");
//        scene.getStylesheets().add("/view/styles/dark_theme.css");
        stageWindow.setScene(scene);

        T controller = loader.getController();

        stageWindow.show();

        return controller;
    }

    public static <ControllerT extends ValueSettable<ParameterT, ResultT>, ParameterT, ResultT> ResultT genericModal(String resource, String title, ParameterT parameter) {
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
        scene.getStylesheets().add("/view/styles/light_theme.css");
//        scene.getStylesheets().add("/view/styles/dark_theme.css");
        stageModal.setTitle(title);

        ControllerT controller = loader.getController();

        if (parameter != null) {
            controller.setParameter(parameter);
        }

        stageModal.showAndWait();

        return controller.getResult();
    }

    // Передать в метод любой элемент который находится в окне, которое нужно закрыть.
    public static void backToLogInWindow() {
        stageWindow.close();
        genericWindow("/view/login_panel.fxml", "Login panel", null);
    }

    public static void closeLogInWindow(){
        stageWindow.close();
    }

    public static void closeModal() {
        stageModal.close();
    }

}
