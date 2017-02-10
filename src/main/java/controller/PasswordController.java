package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.GraphicsLoader;

public class PasswordController {

    @FXML
    private Button okButton;

    @FXML
    public void okButtonAction() {
        GraphicsLoader.closeWindow(okButton);
    }

}
