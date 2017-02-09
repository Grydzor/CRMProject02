package controller;

import entity.Employee;
import enum_types.Position;
import enum_types.Sex;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.Service;
import service.ServiceImpl;
import util.GraphicsLoader;
import util.InputDataChecker;

import java.io.IOException;
import java.util.Optional;


// TODO Добавить закрытие SessionFactory по закрытии окна:
public class AdminController {

    @FXML private ListView<Employee> fldListEmployee;

    @FXML private Button btnCreate;
    @FXML private Button btnDelete;
    @FXML private Button btnChange;
    @FXML private Button btnCancel;
    @FXML private Button btnGenerate;

    @FXML private TextField fldId;
    @FXML private TextField fldName;
    @FXML private TextField fldSurname;
    @FXML private TextField fldAge;
    @FXML private ComboBox<Sex>      boxSex;
    @FXML private ComboBox<Position> boxPosition;

    private Service service;

    public void initialize() {
        service = new ServiceImpl();

        // fill ListView with Employees from DB
        fldListEmployee.setItems(FXCollections.observableArrayList(service.findAll(Employee.class)));
        // set items for ComboBoxes
        boxSex.setItems(FXCollections.observableArrayList(Sex.values()));
        boxPosition.setItems(FXCollections.observableArrayList(Position.values()));

        // change info about Employee when selected item in the ListView is changed
        fldListEmployee.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue)
                        -> fillFieldsWith(newValue));
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
        Employee selEmpl = fldListEmployee.getSelectionModel().getSelectedItem();
        if (btnChange.getText().equals("Change")) {
            if (selEmpl == null) return;

            changeInfo(true);
            return;
        }

        // todo Поставить Listener на изменение TextFields и ComboBoxes
        if (btnChange.getText().equals("Apply")) {
            String name = InputDataChecker.checkString(fldName);
            String surname = InputDataChecker.checkString(fldSurname);
            Integer age = InputDataChecker.checkInteger(fldAge);
            Sex sex = boxSex.getValue();
            Position position = boxPosition.getValue();

            if (name != null && surname != null && age != null) {
                boolean changed = false;
                if (!name.equals(selEmpl.getName())) {selEmpl.setName(name); changed = true; }
                if (!surname.equals(selEmpl.getSurname())) {selEmpl.setSurname(surname); changed = true;}
                if (!age.equals(selEmpl.getAge())) {selEmpl.setAge(age); changed = true;}
                if (!sex.equals(selEmpl.getSex())) {selEmpl.setSex(sex); changed = true;}
                if (!position.equals(selEmpl.getPosition())) {selEmpl.setPosition(position); changed = true;}

                changeInfo(false);
                if (changed) {
                    service.update(selEmpl);
                }
            }
        }
    }

    @FXML
    public void cancelButtonAction() {
        fillFieldsWith(fldListEmployee.getSelectionModel().getSelectedItem());

        changeInfo(false);
    }

    @FXML
    public void deleteButtonAction() {
        Employee employee = fldListEmployee.getSelectionModel().getSelectedItem();
        if (employee != null) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Delete employee with ID " + employee.getId());
            alert.setContentText("Are you sure?");

            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            alert.getButtonTypes().addAll(yes, no);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == yes) {
                service.delete(employee);
                fldListEmployee.getItems().remove(employee);
            }
        }
    }

    private void fillFieldsWith(Employee newValue) {
        fldId       .setText(newValue.getId().toString());
        fldName     .setText(newValue.getName());
        fldSurname  .setText(newValue.getSurname());
        fldAge      .setText(newValue.getAge().toString());
        boxSex      .setValue(newValue.getSex());
        boxPosition .setValue(newValue.getPosition());
    }

    private void changeInfo(Boolean enableFields) {
        fldListEmployee.setDisable(enableFields);
        fldName.setDisable(!enableFields);
        fldSurname.setDisable(!enableFields);
        fldAge.setDisable(!enableFields);
        boxSex.setDisable(!enableFields);
        boxPosition.setDisable(!enableFields);

        btnCreate.setDisable(enableFields);
        btnDelete.setDisable(enableFields);
        btnGenerate.setDisable(enableFields);

        if (enableFields) {
            btnChange.setText("Apply");
            btnChange.setStyle("-fx-base: #b6e7c9;");
        } else {
            btnChange.setText("Change");
            btnChange.setStyle("-fx-base: #ececec;");
        }

        btnCancel.setVisible(enableFields);
    }
}
