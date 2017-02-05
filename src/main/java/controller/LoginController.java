package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField fldLogin;

    @FXML
    private PasswordField fldPassword;

    @FXML
    private Button btnEnter, btnExit;

    public void enterButtonAction() {

    }

    public void exitButtonAction() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }


}
