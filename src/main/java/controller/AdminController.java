package controller;

import entity.Employee;
import enum_types.Position;
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
    @FXML private Button btnRevert;
    @FXML private Button btnGenerate;

    @FXML private TextField     fldId;
    @FXML private TextField     fldName;
    @FXML private TextField     fldSurname;
    @FXML private TextField     fldAge;
    @FXML private ComboBox<Sex> boxSex;
    @FXML private ComboBox<Position> boxPosition;

    private Service service;

    public void initialize() {
        service = new ServiceImpl();

        fldListEmployee.setItems(FXCollections.observableArrayList(service.findAll(Employee.class)));
        boxSex.setItems(FXCollections.observableArrayList(Sex.values()));
        boxPosition.setItems(FXCollections.observableArrayList(Position.values()));

        fldListEmployee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
            @Override
            public void changed(ObservableValue<? extends Employee> observable, Employee oldValue, Employee newValue) {
                fillFieldWith(newValue);
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
            fldListEmployee.getItems().add(newEmployee);
        }
    }

    @FXML
    public void changeButtonAction() throws IOException {
        if (fldListEmployee.getSelectionModel().getSelectedItem() == null) {return;}

        fldListEmployee.setDisable(true);
        fldId.setDisable(false);
        fldName.setDisable(false);
        fldSurname.setDisable(false);
        fldAge.setDisable(false);
        boxSex.setDisable(false);
        boxPosition.setDisable(false);

        btnCreate.setDisable(true);
        btnDelete.setDisable(true);
        btnGenerate.setDisable(true);

        btnChange.setText("Apply");
        btnChange.setStyle("-fx-base: #b6e7c9;");


        btnRevert.setVisible(true);
    }

    @FXML
    public void revertButtonAction() {
        fillFieldWith(fldListEmployee.getSelectionModel().getSelectedItem());

        fldListEmployee.setDisable(false);
        fldId.setDisable(true);
        fldName.setDisable(true);
        fldSurname.setDisable(true);
        fldAge.setDisable(true);
        boxSex.setDisable(true);
        boxPosition.setDisable(true);

        btnCreate.setDisable(false);
        btnDelete.setDisable(false);
        btnGenerate.setDisable(false);

        btnChange.setText("Change");
        btnChange.setStyle("-fx-base: #ececec;");

        btnRevert.setVisible(false);
    }

    private void fillFieldWith(Employee newValue) {
        fldId       .setText(newValue.getId().toString());
        fldName     .setText(newValue.getName());
        fldSurname  .setText(newValue.getSurname());
        fldAge      .setText(newValue.getAge().toString());
        boxSex      .setValue(newValue.getSex());
        boxPosition .setValue(newValue.getPosition());
    }
}
