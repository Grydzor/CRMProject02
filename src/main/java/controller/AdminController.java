package controller;

import entity.Employee;
import enum_types.Sex;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.Service;
import service.ServiceImpl;
import util.GraphicsLoader;

import java.io.IOException;


// TODO Добавить закрытие SessionFactory по закрытии окна:
public class AdminController {

    @FXML private ListView<Employee> fldListEmployee;

    @FXML private Button btnCreate;
    @FXML private Button btnDelete;
    @FXML private Button btnChange;
    @FXML private Button btnGenerate;

    @FXML private TextField     fldId;
    @FXML private TextField     fldName;
    @FXML private TextField     fldSurname;
    @FXML private TextField     fldAge;
    @FXML private ComboBox<Sex> boxSex;
    @FXML private ComboBox<Sex> boxPosition;

    private Service service;

    public void initialize() {
        service = new ServiceImpl();

        fldListEmployee.setItems(FXCollections.observableArrayList(service.findAll(Employee.class)));

        fldListEmployee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observable, Employee oldValue, Employee newValue) {
                fldId       .setText(newValue.getId().toString());
                fldName     .setText(newValue.getName());
                fldSurname  .setText(newValue.getSurname());
                fldAge      .setText(newValue.getAge().toString());
                boxSex      .setPromptText(newValue.getSex().toString());
                boxPosition .setPromptText(newValue.getPosition().toString());
            }
        });
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
