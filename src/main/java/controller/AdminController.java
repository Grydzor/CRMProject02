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

/* Controller for Admin panel */
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

    /* Loading of Employees list */
    public void initialize() {
        service = new ServiceImpl();

        // set columns in TableView
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        // fill TableView with Employees from DB
        employees = FXCollections.observableArrayList(service.findAll(Employee.class));
        tableEmployees.setItems(employees);

        // set default items for ComboBoxes
        boxSex.setItems(FXCollections.observableArrayList(Sex.values()));
        boxPosition.setItems(FXCollections.observableArrayList(Position.values()));

        // change info about Employee when selected item in the ListView is changed
        tableEmployees.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue)

                        -> fillFieldsWith(newValue));
        // add listeners for changing TextFields & ComboBoxes
        // it activates Apply button
        addListeners();
    }

    /* Action for Create button click */
    @FXML
    public void createButtonAction() throws IOException {
        // Shows Create window for new Employee
        CreateController createController = GraphicsLoader.newWindowGeneric(
                "/view/create_panel.fxml", "Create...", true);

        // Gets employee from Create Controller
        // and adds to DB and TableView if it is not null
        Employee newEmployee;
        if ((newEmployee = createController.getEmployee()) != null) {
            service.add(newEmployee);
            tableEmployees.getItems().add(newEmployee);
        }
    }

    /* Action for Change button click
    *  Change button has two forms: 'Change' and 'Apply'
    *  Default form - 'Change'. It provides the ability to change
    *  info about Employee. When clicked: 'Change' -> 'Apply'
    *  'Apply' form provides saving changes.
    *  Also Cancel button becomes available to revert changes.
    * */
    @FXML
    public void changeButtonAction() throws IOException {
        // selected Employee
        Employee selEmpl = tableEmployees.getSelectionModel().getSelectedItem();

        // actions for 'Change' form
        if (btnChange.getText().equals("Change")) {
            if (selEmpl == null) return;

            // makes all TextFields and ComboBoxes editable
            changeInfo(true);
            return;
        }

        // actions for 'Apply' form
        if (btnChange.getText().equals("Apply")) {
            // validate TextFields
            String name = InputDataChecker.checkString(fldName);
            String surname = InputDataChecker.checkString(fldSurname);
            Integer age = InputDataChecker.checkInteger(fldAge);
            Sex sex = boxSex.getValue();
            Position position = boxPosition.getValue();

            // If Fields are correct, apply changes
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

    // Action for Cancel button click
    @FXML
    public void cancelButtonAction() {
        fillFieldsWith(tableEmployees.getSelectionModel().getSelectedItem());

        changeInfo(false);
    }

    /* Action for Generate button click
    *  Generates User account for selected Employee
    * */
    @FXML
    public void generateButtonAction() {
        Employee selectedEmployee = tableEmployees.getSelectionModel().getSelectedItem();

        // Check if account doesn't exist
        // if so then create acc and add it to DB
        if (selectedEmployee.getUser() == null) {
            User user = new User(
                    selectedEmployee.getName() + " " + selectedEmployee.getSurname(),
                    "qwerty",
                    selectedEmployee
            );
            service.add(user);
            selectedEmployee.setUser(user);
            service.update(selectedEmployee);

            // User account has been generated,
            // so make the button invisible
            btnGenerate.setVisible(false);
        }
    }

    /* Action for Delete button click */
    @FXML
    public void deleteButtonAction() {
        Employee employee = tableEmployees.getSelectionModel().getSelectedItem();
        if (employee != null) {
            // Ask again
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Delete employee with ID " + employee.getId());
            alert.setContentText("Are you sure?");

            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            alert.getButtonTypes().addAll(yes, no);

            Optional<ButtonType> result = alert.showAndWait();

            // If 'yes'
            if (result.get() == yes) {
                User user = employee.getUser();
                // Delete dependency on User
                employee.setUser(null);
                service.update(employee);
                // Safely remove user
                // we can do this, because employee
                // does not depend on it
                service.delete(user);
                // then delete employee
                service.delete(employee);
                // refresh TableView
                tableEmployees.getItems().remove(employee);
            }
        }
    }

    /* Supplementary method
    * When Employee is selected in TableView,
    * his info is displayed on the right
    * */
    private void fillFieldsWith(Employee newValue) {
        if (newValue == null) return;

        // Fill TextField & ComboBoxes
        fldId       .setText(newValue.getId().toString());
        fldName     .setText(newValue.getName());
        fldSurname  .setText(newValue.getSurname());
        fldAge      .setText(newValue.getAge().toString());
        boxSex      .setValue(newValue.getSex());
        boxPosition .setValue(newValue.getPosition());

        // Special case for field 'Has account?'
        if (newValue.getUser() == null) {
            // Show Generate button if user isn't exists
            btnGenerate.setVisible(true);
            imageTick  .setVisible(false);
        } else {
            // Show Tick image if user exists
            btnGenerate.setVisible(false);
            imageTick  .setVisible(true);
        }
    }

    /* Supplementary method
     * Enabling fields for changing when true
     */
    private void changeInfo(Boolean enableFields) {
        // Disable TableView & Buttons
        tableEmployees.setDisable(enableFields);
        btnCreate     .setDisable(enableFields);
        btnDelete     .setDisable(enableFields);
        btnGenerate   .setDisable(enableFields);
        btnChange     .setDisable(enableFields);

        // Enable fields
        fldName       .setDisable(!enableFields);
        fldSurname    .setDisable(!enableFields);
        fldAge        .setDisable(!enableFields);
        boxSex        .setDisable(!enableFields);
        boxPosition   .setDisable(!enableFields);

        // Enable Cancel Button
        btnCancel.setVisible(enableFields);

        if (enableFields) {
            // to 'Apply' form
            btnChange.setText("Apply");
            btnChange.setStyle("-fx-base: #b6e7c9;");
        } else {
            // to 'Change' form
            btnChange.setText("Change");
            btnChange.setStyle("-fx-base: #ececec;");

            // Set default borders
            fldName     .setStyle("-fx-border-color: transparent;");
            fldSurname  .setStyle("-fx-border-color: transparent;");
            fldAge      .setStyle("-fx-border-color: transparent;");
            boxSex      .setStyle("-fx-border-color: transparent;");
            boxPosition .setStyle("-fx-border-color: transparent;");
        }
    }

    // Create listener for Fields & ComboBoxes
    // when text or value is changing
    // then Change button becomes active
    private void addListeners() {
        ChangeListener<? super Object> listener = (observable, oldValue, newValue) -> btnChange.setDisable(false);
        fldName     .textProperty().addListener(listener);
        fldSurname  .textProperty().addListener(listener);
        fldAge      .textProperty().addListener(listener);
        boxSex      .valueProperty().addListener(listener);
        boxPosition .valueProperty().addListener(listener);
    }
}
