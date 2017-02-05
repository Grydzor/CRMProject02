package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private TextArea fldListEmployee, fldDataEmployee;

    @FXML
    private Button btnCreate, btnDelete, btnChange, btnGenerate;

    public void createButtonAction() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/create_panel.fxml"));
        stage.setTitle("Create...");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
        Stage old = (Stage) btnCreate.getScene().getWindow();
        old.close();
    }

}
