package util;

import controller.MainController;
import controller.modal.ModalController;
import entity.UserSession;
import javafx.application.Platform;
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

    public static <ControllerT extends MainController> void genericWindow(String resource, String title, Long userId) {
        Double currentWidth = stageWindow.getWidth();
        Double currentHeight = stageWindow.getHeight();

        FXMLLoader loader = new FXMLLoader(StageFactory.class.getResource(resource));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Проблема в пути к FXML");
            return;
        }
        stageWindow.setTitle(title);

        // Important: UserSession is setting after initialize() method in Controller
        // Controller loads in 'loader.load()'
        if (!Long.valueOf(-1L).equals(userId)) {
            UserSession session = ApplicationContextFactory.getApplicationContext()
                    .getBean(UserSessionService.class).writeToResource(userId);
            if (userId != null) {
                ControllerT controller = loader.getController();
                controller.setUserSession(session);
            }
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/view/styles/light_theme.css");
//        scene.getStylesheets().add("/view/styles/dark_theme.css");
        stageWindow.setScene(scene);

        stageWindow.setWidth(currentWidth);
        stageWindow.setHeight(currentHeight);

        stageWindow.show();
    }

    public static <ControllerT extends ModalController<ParameterT, ResultT>, ParameterT, ResultT> ResultT genericModal(String resource, String title, ParameterT parameter) {
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
