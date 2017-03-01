package controller;

import entity.User;
import enum_types.UserStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.context.ApplicationContext;
import service.UserService;
import service.UserSessionService;
import util.ApplicationContextFactory;
import util.HibernateSessionFactory;
import util.InputDataChecker;
import util.StageFactory;

import java.io.IOException;

public class LoginController {

    @FXML private TextField fldLogin;
    @FXML private PasswordField fldPassword;

    @FXML private Label lblStatus;

    private UserService userService;
    private UserSessionService sessionService;
    private ApplicationContext context;

    public void initialize() {
        context = ApplicationContextFactory.getApplicationContext();
        userService = context.getBean(UserService.class);
        sessionService = context.getBean(UserSessionService.class);
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

    @FXML
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
                switch (user.getEmployee().getPosition()) {
                    case ADMIN:
                        StageFactory.genericWindow("/view/admin_panel_two.fxml", "Administration", user.getId());
                        break;
                    case MANAGER:
                        StageFactory.genericWindow("/view/manager_panel.fxml", "Management", user.getId());
                        break;
                    case CASHIER:
                        StageFactory.genericWindow("/view/cashier_panel.fxml", "Cashier", user.getId());
                        break;
                    case STOREKEEPER:
                        StageFactory.genericWindow("/view/storage_panel_two.fxml", "Storage", user.getId());
                        break;
                }
                return;
            }

            setStatusMsg(UserStatus.WRONG_PASSWORD);
        }
    }

    @FXML
    public void exitButtonAction() {
        StageFactory.closeLogInWindow();
        HibernateSessionFactory.getSessionFactory().close();
    }

    private void setStatusMsg(UserStatus userStatus) {
        lblStatus.setText(userStatus.toString());
    }
}
