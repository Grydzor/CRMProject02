package controller;

import entity.User;
import enum_types.UserStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.Service;
import service.UserService;
import service.UserServiceImpl;
import util.StageFactory;
import util.HibernateSessionFactory;
import util.InputDataChecker;

import java.io.IOException;

public class LoginController {

    @FXML private TextField fldLogin;
    @FXML private PasswordField fldPassword;

    @FXML private Button btnEnter;
    @FXML private Button btnExit;

    @FXML private Label lblStatus;

    private UserService userService;

    public void initialize() {
        userService = new UserServiceImpl();
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
                б. совпадает - SUCCESS(UserStatus)- закрываем окно (StageFactory.closeWindow),
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
                setStatusMsg(UserStatus.SUCCESS);
                StageFactory.closeWindow();
                switch (user.getEmployee().getPosition()) {
                    case ADMIN:
                        StageFactory.genericWindow("/view/admin_panel.fxml", "Administration");
                        break;
                    case MANAGER:
                        StageFactory.genericWindow("/view/manager_panel.fxml", "Management");
                        break;
                }
                return;
            }

            setStatusMsg(UserStatus.WRONG_PASSWORD);
        }
    }

    public void exitButtonAction() {
        StageFactory.closeWindow();
        HibernateSessionFactory.getSessionFactory().close();
    }

    private void setStatusMsg(UserStatus userStatus) {
        lblStatus.setText(userStatus.toString());
    }
}
