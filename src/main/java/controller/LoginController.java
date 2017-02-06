package controller;

import entity.User;
import enum_types.Position;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.EmployeeService;
import service.Service;
import service.ServiceImpl;
import service.UserService;

import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML
    private TextField fldLogin;

    @FXML
    private PasswordField fldPassword;

    @FXML
    private Button btnEnter, btnExit;

    private UserService userService;
    private EmployeeService employeeService;

    public void enterButtonAction() throws IOException {
        Boolean isAdmin = false;
        Boolean isManager = false;

        Service service = new ServiceImpl();
        List<User> users = service.findAll(User.class);

        for (User user : users) {
            if (user.getLogin().equals(fldLogin.getText())
                    && user.getPassword().equals(fldPassword.getText())) {
                if (user.getEmployee().getPosition() == Position.ADMIN) {
                    isAdmin = true;
                } else if (user.getEmployee().getPosition() == Position.MANAGER) {
                    isManager = true;
                }
            }
        }
        if (isAdmin) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/admin_panel.fxml"));
            stage.setTitle("Administration");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            Stage old = (Stage) btnEnter.getScene().getWindow();
            old.close();
        } else if (isManager) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/manager_panel.fxml"));
            stage.setTitle("Management");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            Stage old = (Stage) btnEnter.getScene().getWindow();
            old.close();
        } else {
            fldLogin.setText("");
            fldPassword.setText("");
            fldPassword.setPromptText("PASSWORD IS WRONG!");
        }
    }

    public void exitButtonAction() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

}
