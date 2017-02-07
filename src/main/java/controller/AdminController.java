package controller;

import entity.Employee;
import enum_types.Position;
import enum_types.Sex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;
import service.ServiceImpl;
import util.GraphicsLoader;

import java.io.IOException;


// TODO Добавить закрытие SessionFactory по закрытии окна:
public class AdminController {

    @FXML private ListView<Employee> fldListEmployee;
    @FXML private TableView<Employee> tblEmployeeData;

    @FXML private Button btnCreate;
    @FXML private Button btnDelete;
    @FXML private Button btnChange;
    @FXML private Button btnGenerate;

    private Service service = new ServiceImpl();

    public void initialize() {
        fldListEmployee.setItems(FXCollections.observableArrayList(service.findAll(Employee.class)));
    }

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
