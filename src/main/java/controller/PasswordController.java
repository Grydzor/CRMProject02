package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.StageFactory;

public class PasswordController {

    @FXML
    private Button okButton;

    @FXML
    public void okButtonAction() {
        StageFactory.closeWindow();
    }

}
