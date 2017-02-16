package controller;

import entity.User;
import enum_types.UserStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    private UserService service;

    public void initialize() {
        service = new UserServiceImpl();
    }

    public void enterButtonAction() throws IOException {
        String login = InputDataChecker.checkString(fldLogin);
        String password = InputDataChecker.checkString(fldPassword);

        if (login != null && password != null) {

            User user = service.find(login);

            if (user == null) {
                setStatusMsg(UserStatus.UNKNOWN_USER);
                return;
            }

            if (user.getPassword().equals(password)) {
                setStatusMsg(UserStatus.SUCCESS);
                switch (user.getEmployee().getPosition()) {
                    case ADMIN:
                        StageFactory.genericWindow("/view/admin_panel.fxml", "Administration");
                        break;
                    case MANAGER:
                        StageFactory.genericWindow("/view/manager_panel.fxml", "Management");
                        break;
                }
                StageFactory.closeWindow(btnEnter);
                return;
            }

            setStatusMsg(UserStatus.WRONG_PASSWORD);
        }
    }

    public void exitButtonAction() {
        StageFactory.closeWindow(btnExit);
        HibernateSessionFactory.getSessionFactory().close();
    }

    private void setStatusMsg(UserStatus userStatus) {
        lblStatus.setText(userStatus.toString());
    }
}
