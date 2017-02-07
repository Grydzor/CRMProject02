package controller;

import entity.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import service.Service;
import service.ServiceImpl;
import util.GraphicsLoader;

import java.io.IOException;


// TODO Добавить закрытие SessionFactory по закрытии окна:
public class AdminController {

    @FXML private TextArea fldListEmployee;
    @FXML private TextArea fldDataEmployee;

    @FXML private Button btnCreate;
    @FXML private Button btnDelete;
    @FXML private Button btnChange;
    @FXML private Button btnGenerate;

    private Service service = new ServiceImpl();

    @FXML
    public void createButtonAction() throws IOException {
        CreateController createController = GraphicsLoader.newWindowGeneric(
                "/view/create_panel.fxml", "Create...", true);

        Employee newEmployee;
        if ((newEmployee = createController.getEmployee()) != null) {
            service.add(newEmployee);
        }
    }
}
