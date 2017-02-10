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
import util.InputDataChecker;

import java.io.IOException;

/* Controller for Admin panel */
public class AdminController {
    @FXML private TableView<Employee> employeesTable;
          private ObservableList<Employee> employees;
    @FXML private TableColumn<Employee, Long> idColumn;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> surnameColumn;

    @FXML private Button createButton;
    @FXML private Button addButton;
    @FXML private Button cancelCreatingButton;
    @FXML private Button deleteButton;
    @FXML private Button applyDeletingButton;
    @FXML private Button cancelDeletingButton;
    @FXML private Button changeButton;
    @FXML private Button applyButton;
    @FXML private Button cancelChangingButton;
    @FXML private Button generateButton;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<Sex> sexBox;
    @FXML private ComboBox<Position> positionBox;
    @FXML private ImageView tickImage;

    private String name;
    private String surname;
    private Integer age;
    private Sex sex;
    private Position position;

    private Employee currentEmployee;

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
        employeesTable.setItems(employees);

        // set default items for ComboBoxes
        sexBox.setItems(FXCollections.observableArrayList(Sex.values()));
        positionBox.setItems(FXCollections.observableArrayList(Position.values()));

        // Save current Employee
        // change info about Employee when selected item in the TableView is changed
        addSelectListener();
        // add Button listeners for changing TextFields & ComboBoxes
        // it activates Apply / Add button
        addChangeListenerFor(applyButton);
        addChangeListenerFor(addButton);
    }

    /* Action for Create button click */
    @FXML
    public void create() throws IOException {
        fillFieldsWith(null);
        idField.setText("will be generated");
        sexBox.setPromptText("Choose one");
        positionBox.setPromptText("Choose one");

        generateButton.setVisible(true);
        generateButton.setDisable(true);

        disableAnother(true, createButton, addButton, cancelCreatingButton);
        openFields(true);
    }

    // Adds new Employee to DB
    @FXML
    public void add() throws IOException {
        validateTextFields();

        if (name != null && surname != null && age != null && sex != null && position != null) {
            Employee employee = new Employee(name, surname, age, sex, position);

            service.add(employee);
            employees.add(employee);
            employeesTable.getSelectionModel().select(employee);

            fillFieldsWith(currentEmployee);

            disableAnother(false, createButton, addButton, cancelCreatingButton);
            openFields(false);
        }
    }

    @FXML
    public void cancelCreating() {
        fillFieldsWith(currentEmployee);

        disableAnother(false, createButton, addButton, cancelCreatingButton);
        openFields(false);
    }

    /* Action for Change button click
    *  It provides the ability to change info about Employee */
    @FXML
    public void change() throws IOException {

        if (currentEmployee == null) return;

        // makes all TextFields and ComboBoxes editable
        disableAnother(true, changeButton, applyButton, cancelChangingButton);
        openFields(true);
    }

    /* Action for Apply button click
     * Provides saving changes
     * Also Cancel button becomes available to revert changes */
    @FXML
    public void apply() {

        // validate TextFields
        validateTextFields();

        // If Fields are correct, apply changes
        if (name != null && surname != null && age != null) {
            if (!name.equals(currentEmployee.getName()))            currentEmployee.setName(name);
            if (!surname.equals(currentEmployee.getSurname()))      currentEmployee.setSurname(surname);
            if (!age.equals(currentEmployee.getAge()))              currentEmployee.setAge(age);
            if (!sex.equals(currentEmployee.getSex()))              currentEmployee.setSex(sex);
            if (!position.equals(currentEmployee.getPosition()))    currentEmployee.setPosition(position);

            disableAnother(false, changeButton, applyButton, cancelChangingButton);
            openFields(false);

            service.update(currentEmployee);
        }
    }

    // Action for Cancel button click
    @FXML
    public void cancelChanging() {
        fillFieldsWith(currentEmployee);

        disableAnother(false, changeButton, applyButton, cancelChangingButton);
        openFields(false);

    }

    /* Action for Generate button click
    *  Generates User account for selected Employee
    * */
    @FXML
    public void generate() {
        // Check if account doesn't exist
        // if so then create acc and addButton it to DB
        if (currentEmployee.getUser() == null) {
            User user = new User(
                    currentEmployee.getName() + "." + currentEmployee.getSurname(),
                    "qwerty",
                    currentEmployee
            );
            service.add(user);
            currentEmployee.setUser(user);
            service.update(currentEmployee);

            // User account has been generated,
            // so make the button invisible
            generateButton.setVisible(false);
            tickImage.setVisible(true);
        }
    }

    /* Action for Delete button click */
    @FXML
    public void delete() {
        if (currentEmployee != null) {
            disableAnother(true, deleteButton, applyDeletingButton, cancelDeletingButton);
            applyDeletingButton.setDisable(false);
            cancelDeletingButton.requestFocus();
        }
    }

    @FXML
    public void applyDeleting() {
        User user = currentEmployee.getUser();
        // Delete dependency on User
        currentEmployee.setUser(null);
        service.update(currentEmployee);
        // Safely remove user
        // we can do this, because employee
        // does not depend on it
        service.delete(user);
        // then delete employee
        service.delete(currentEmployee);
        // refresh TableView
        employeesTable.getItems().remove(currentEmployee);

        disableAnother(false, deleteButton, applyDeletingButton, cancelDeletingButton);
    }

    @FXML
    public void cancelDeleting() {
        disableAnother(false, deleteButton, applyDeletingButton, cancelDeletingButton);
    }

    /* Supplementary method
    * When Employee is selected in TableView,
    * his info is displayed on the right
    * */
    private void fillFieldsWith(Employee employee) {
        // Clears all Employee details
        if (employee == null) {
            // Fields
            idField.setText("");
            nameField.setText("");
            surnameField.setText("");
            ageField.setText("");
            // Boxes
            sexBox.setPromptText("");
            sexBox.setValue(null);
            positionBox.setPromptText("");
            positionBox.setValue(null);
            // 'Has account?'
            generateButton.setVisible(false);
            tickImage.setVisible(false);

            return;
        }

        // Fill TextField & ComboBoxes with employee details
        idField.setText(employee.getId().toString());
        nameField.setText(employee.getName());
        surnameField.setText(employee.getSurname());
        ageField.setText(employee.getAge().toString());
        sexBox.setValue(employee.getSex());
        positionBox.setValue(employee.getPosition());

        // Special case for field 'Has account?'
        Boolean userExists = (employee.getUser() != null);
        generateButton.setVisible(!userExists);
        generateButton.setDisable(false);
        tickImage.setVisible(userExists);
    }

    /* Supplementary method
     * Disables another elements in scene
     */
    private void disableAnother(boolean bool, Button actionButton,
                            Button applyButton, Button cancelButton) {
        employeesTable.setDisable(bool);
        disableButtons(bool);

        actionButton.setVisible(!bool);
        applyButton.setVisible(bool);
        cancelButton.setVisible(bool);

        applyButton.setDisable(bool);
    }

    private void openFields(boolean bool) {
        disableFields(!bool);
        setDefaultBordersForFields(!bool);
    }

    private void setDefaultBordersForFields(Boolean bool) {
        if (bool) {
            nameField.setStyle("-fx-border-color: transparent;");
            surnameField.setStyle("-fx-border-color: transparent;");
            ageField.setStyle("-fx-border-color: transparent;");
            sexBox.setStyle("-fx-border-color: transparent;");
            positionBox.setStyle("-fx-border-color: transparent;");
        }
    }

    private void disableFields(Boolean bool) {
        // Disable fields
        nameField.setDisable(bool);
        surnameField.setDisable(bool);
        ageField.setDisable(bool);
        sexBox.setDisable(bool);
        positionBox.setDisable(bool);
    }

    private void disableButtons(Boolean bool) {
        // Disable Buttons
        createButton.setDisable(bool);
        deleteButton.setDisable(bool);
        generateButton.setDisable(bool);
        changeButton.setDisable(bool);
    }

    // Create listener for Fields & ComboBoxes
    // when text or value is changing
    // then Change button becomes active
    private void addChangeListenerFor(Button button) {
        ChangeListener<? super Object> listener = (observable, oldValue, newValue) -> button.setDisable(false);
        nameField.textProperty().addListener(listener);
        surnameField.textProperty().addListener(listener);
        ageField.textProperty().addListener(listener);
        sexBox.valueProperty().addListener(listener);
        positionBox.valueProperty().addListener(listener);
    }

    private void addSelectListener() {
        employeesTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            currentEmployee = newValue;
            fillFieldsWith(currentEmployee);

            boolean isNull = (currentEmployee == null);
            changeButton.setDisable(isNull);
            deleteButton.setDisable(isNull);
        });
    }

    private void validateTextFields() {
        name = InputDataChecker.checkString(nameField);
        surname = InputDataChecker.checkString(surnameField);
        age = InputDataChecker.checkInteger(ageField);
        sex = sexBox.getValue();
        position = positionBox.getValue();
    }
}
