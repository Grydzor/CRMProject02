package controller;

import entity.Employee;
import entity.User;
import enum_types.Position;
import enum_types.Sex;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import service.Service;
import service.ServiceImpl;
import util.GraphicsLoader;
import util.InputDataChecker;

import java.io.IOException;
import java.util.Optional;

public class AdminController {

    @FXML private TableView<Employee> tableEmployees;
          private ObservableList<Employee> employees;
    @FXML private TableColumn<Employee, Long> idColumn;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> surnameColumn;

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
    @FXML private ImageView          imageTick;

    private Service service;

    public void initialize() {
        service = new ServiceImpl();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        // fill ListView with Employees from DB
        employees = FXCollections.observableArrayList(service.findAll(Employee.class));
        tableEmployees.setItems(employees);
        // set items for ComboBoxes
        boxSex.setItems(FXCollections.observableArrayList(Sex.values()));
        boxPosition.setItems(FXCollections.observableArrayList(Position.values()));

        // change info about Employee when selected item in the ListView is changed
        tableEmployees.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue)
                        -> fillFieldsWith(newValue));

        addListeners();
    }

    @FXML
    public void createButtonAction() throws IOException {
        CreateController createController = GraphicsLoader.newWindowGeneric(
                "/view/create_panel.fxml", "Create...", true);

        Employee newEmployee;
        if ((newEmployee = createController.getEmployee()) != null) {
            service.add(newEmployee);
            tableEmployees.getItems().add(newEmployee);
        }
    }

    @FXML
    public void changeButtonAction() throws IOException {
        Employee selEmpl = tableEmployees.getSelectionModel().getSelectedItem();
        if (btnChange.getText().equals("Change")) {
            if (selEmpl == null) return;

            changeInfo(true);
            return;
        }

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
        fillFieldsWith(tableEmployees.getSelectionModel().getSelectedItem());

        changeInfo(false);
    }

    @FXML
    public void generateButtonAction() {
        Employee selectedEmployee = tableEmployees.getSelectionModel().getSelectedItem();
        if (selectedEmployee.getUser() == null) {
            User user = new User(
                    selectedEmployee.getName() + " " + selectedEmployee.getSurname(),
                    "qwerty",
                    selectedEmployee
            );
            service.add(user);
            selectedEmployee.setUser(user);
            service.update(selectedEmployee);
            btnGenerate.setVisible(false);
        }/* else {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Warning");
            alert.setContentText("Employee has got an account!");
            ButtonType ok = new ButtonType("Ok");
            alert.getButtonTypes().addAll(ok);
            alert.showAndWait();
        }*/
    }

    @FXML
    public void deleteButtonAction() {
        Employee employee = tableEmployees.getSelectionModel().getSelectedItem();
        if (employee != null) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Delete employee with ID " + employee.getId());
            alert.setContentText("Are you sure?");

            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            alert.getButtonTypes().addAll(yes, no);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == yes) {
                User user = employee.getUser();
                employee.setUser(null);
                service.update(employee);
                service.delete(user);
                service.delete(employee);
                tableEmployees.getItems().remove(employee);
            }
        }
    }

    private void fillFieldsWith(Employee newValue) {
        if (newValue == null) return;

        fldId       .setText(newValue.getId().toString());
        fldName     .setText(newValue.getName());
        fldSurname  .setText(newValue.getSurname());
        fldAge      .setText(newValue.getAge().toString());
        boxSex      .setValue(newValue.getSex());
        boxPosition .setValue(newValue.getPosition());

        if (newValue.getUser() == null) {
            btnGenerate.setVisible(true);
            imageTick  .setVisible(false);
        } else {
            btnGenerate.setVisible(false);
            imageTick  .setVisible(true);
        }
    }

    private void changeInfo(Boolean enableFields) {
        tableEmployees.setDisable(enableFields);
        fldName       .setDisable(!enableFields);
        fldSurname    .setDisable(!enableFields);
        fldAge        .setDisable(!enableFields);
        boxSex        .setDisable(!enableFields);
        boxPosition   .setDisable(!enableFields);

        btnCreate     .setDisable(enableFields);
        btnDelete     .setDisable(enableFields);
        btnGenerate   .setDisable(enableFields);
        btnChange     .setDisable(enableFields);

        if (enableFields) {
            btnChange.setText("Apply");
            btnChange.setStyle("-fx-base: #b6e7c9;");
        } else {
            btnChange.setText("Change");
            btnChange.setStyle("-fx-base: #ececec;");

            fldName     .setStyle("-fx-border-color: transparent;");
            fldSurname  .setStyle("-fx-border-color: transparent;");
            fldAge      .setStyle("-fx-border-color: transparent;");
            boxSex      .setStyle("-fx-border-color: transparent;");
            boxPosition .setStyle("-fx-border-color: transparent;");
        }

        btnCancel.setVisible(enableFields);
    }

    private void addListeners() {
        ChangeListener<? super Object> listener = (observable, oldValue, newValue) -> btnChange.setDisable(false);
        fldName     .textProperty().addListener(listener);
        fldSurname  .textProperty().addListener(listener);
        fldAge      .textProperty().addListener(listener);
        boxSex      .valueProperty().addListener(listener);
        boxPosition .valueProperty().addListener(listener);
    }
}
