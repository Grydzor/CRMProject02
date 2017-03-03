package controller;

import entity.User;
import entity.UserSession;
import enum_types.UserStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.context.ApplicationContext;
import service.EmployeeService;
import service.UserService;
import service.UserSessionService;
import util.ApplicationContextFactory;
import util.EmailSender;
import util.InputDataChecker;
import util.StageFactory;

import java.io.IOException;

import static util.InputDataChecker.checkEmail;

public class LoginController {

    @FXML private Label loginHintLabel;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Label forgotPassLabel;

    @FXML private Label emailHintLabel;
    @FXML private TextField emailField;
    @FXML private Button resetPasswordButton;

    private UserService userService;
    private UserSessionService sessionService;
    private ApplicationContext context;

    private UserSession session;

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
        String login = InputDataChecker.checkString(loginField);
        String password = InputDataChecker.checkString(passwordField);

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
                        StageFactory.loadWindow("/view/admin_panel.fxml", "Administration", user.getId());
                        break;
                    case MANAGER:
                        StageFactory.loadWindow("/view/manager_panel.fxml", "Management", user.getId());
                        break;
                    case CASHIER:
                        StageFactory.loadWindow("/view/cashier_panel.fxml", "Cashier", user.getId());
                        break;
                    case STOREKEEPER:
                        StageFactory.loadWindow("/view/storage_panel_two.fxml", "Storage", user.getId());
                        break;
                }
                return;
            }

            setStatusMsg(UserStatus.WRONG_PASSWORD);
        }
    }

    @FXML
    public void forgotPass() {
        disableForResetPass(true);
    }

    @FXML
    public void resetPassword() {
        String email = InputDataChecker.checkEmail(emailField);
        if (email != null) {
            User user = context.getBean(EmployeeService.class).find(email);
            if (user != null) {
                if (EmailSender.resetPass(user)) {
                    System.out.println("Password is reset!");
                    disableForResetPass(false);
                    return;
                }
            }
        }
        System.out.println("Password is not reset!");
    }

    private void setStatusMsg(UserStatus userStatus) {
        statusLabel.setText(userStatus.toString());
    }

    private void disableForResetPass(Boolean bool) {
        loginHintLabel.setVisible(!bool);
        loginField.setVisible(!bool);
        passwordField.setVisible(!bool);
        statusLabel.setVisible(!bool);
        forgotPassLabel.setVisible(!bool);

        emailHintLabel.setVisible(bool);
        emailField.setVisible(bool);
        resetPasswordButton.setVisible(bool);
    }
}
