package controller;

import entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.Service;
import service.ServiceImpl;
import util.GraphicsLoader;

import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML
    private TextField fldLogin;

    @FXML
    private PasswordField fldPassword;

    @FXML
    private Button btnEnter, btnExit;

    public void enterButtonAction() throws IOException {
        Service service = new ServiceImpl();
        List<User> users = service.findAll(User.class);
        int count = 0;
        for (User user : users) {
            if (user.getLogin().equals(fldLogin.getText()) && user.getPassword().equals(fldPassword.getText())) {
                switch (user.getEmployee().getPosition()) {
                    case ADMIN:
                        GraphicsLoader.newWindow("/view/admin_panel.fxml", "Administration");
                        GraphicsLoader.closeWindow(btnEnter);
                        break;
                    case MANAGER:
                        GraphicsLoader.newWindow("/view/manager_panel.fxml", "Management");
                        GraphicsLoader.closeWindow(btnEnter);
                        break;
                }
            } else if (count == users.size() - 1) {
                GraphicsLoader.newModalWindow("/view/password_alert.fxml", "Oops!");
            } else {
                count++;
            }
        }
    }

    public void exitButtonAction() {
        GraphicsLoader.closeWindow(btnExit);
    }

}
