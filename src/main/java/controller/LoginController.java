package controller;

import entity.User;
import entity.UserSession;
import enum_types.UserStatus;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.UserService;
import service.UserServiceImpl;
import service.UserSessionServiceImpl;
import util.StageFactory;
import util.HibernateSessionFactory;
import util.InputDataChecker;

import java.io.IOException;

public class LoginController {

    @FXML private TextField fldLogin;
    @FXML private PasswordField fldPassword;

    @FXML private Label lblStatus;

    private UserService userService;

    public void initialize() {
        userService = UserServiceImpl.getInstance();

        Platform.runLater(() -> {
            UserSession fromResource = UserSession.readFromResource();
            if (fromResource == null) return;

            UserSession fromDB = UserSessionServiceImpl.getInstance().read(fromResource.getUserId());
            if (fromDB == null) return;

            if (fromResource.getSessionId().equals(fromDB.getSessionId())) logIn(userService.read(fromDB.getUserId()), true);
            else UserSession.writeToResource(null);
        });
    }


    /*
    метод enterButtonAction при нажатии клавиши Enter:
    - принимает данные - login & password (в формате String)
    - обработка и результат:
        1. проверка чтобы поля login & password были заполнены,
        иначе (InputDataChecker.checkString) - поля подсвечиваем красным цветом
        2. подключаем интерфейс userService для поиска логина:
            - если логин не существует - UNKNOWN_USER (UserStatus)
            - иначе (логин существует) - сравниваем password с тем, который в базе:
                а. не совпадает - WRONG_PASSWORD (UserStatus)
                б. совпадает - SUCCESS(UserStatus)- закрываем окно (StageFactory.backToLogInWindow),
                вытягиваем по цепочке через - user.getEmployee().getPosition() - и выбираем -
                ADMIN или MANAGER - необходимую панель (Administration & Management)

     */
    public void enterButtonAction() throws IOException {
        String login = InputDataChecker.checkString(fldLogin);
        String password = InputDataChecker.checkString(fldPassword);

        if (login != null && password != null) {

            User user = userService.find(login);

            if (user == null) {
                setStatusMsg(UserStatus.UNKNOWN_USER);
                return;
            }

            if (user.getPassword().equals(password)) {
                logIn(user, false);
                return;
            }

            setStatusMsg(UserStatus.WRONG_PASSWORD);
        }
    }

    private void logIn(User user, Boolean fromSession) {
        Long userId;
        if (fromSession) userId = -1L;
        else userId = user.getId();
        System.out.println(user.getEmployee().getPosition());
        setStatusMsg(UserStatus.SUCCESS);
//                StageFactory.backToLogInWindow();
        switch (user.getEmployee().getPosition()) {
            case ADMIN:
                StageFactory.genericWindow("/view/admin_panel_two.fxml", "Administration", userId, "/view/styles/light_theme.css");
                break;
            case MANAGER:
                StageFactory.genericWindow("/view/manager_panel.fxml", "Management", userId);
                break;
            case CASHIER:
                StageFactory.genericWindow("/view/cashier_panel.fxml", "Cashier", userId, "/view/styles/light_theme.css");
                break;
            case STOREKEEPER:
                StageFactory.genericWindow("/view/storage_panel_two.fxml", "Storage", userId, "/view/styles/light_theme.css");
                break;
        }
    }

    public void exitButtonAction() {
        StageFactory.closeLogInWindow();
        HibernateSessionFactory.getSessionFactory().close();
    }

    private void setStatusMsg(UserStatus userStatus) {
        lblStatus.setText(userStatus.toString());
    }
}
