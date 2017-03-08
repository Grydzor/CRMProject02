package controller;

import entity.Employee;
import entity.User;
import entity.UserSession;
import enum_types.Position;
import enum_types.Sex;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import service.EmployeeService;
import service.UserService;
import util.*;

import java.io.IOException;

/* Controller for Admin panel */
public class AdminController implements MainController {
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
    @FXML private Button logOutButton;


    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<Sex> sexBox;
    @FXML private ComboBox<Position> positionBox;
    @FXML private TextField emailField;

    @FXML private TextField loginField;
    @FXML private TextField passwordField;

    @FXML private HBox noAccount;
    @FXML private GridPane withAccount;
    @FXML private Label accountInformation;
    @FXML private Separator separatorUnder;
    @FXML private Separator separatorAbove;

    private String name;
    private String surname;
    private Integer age;
    private Sex sex;
    private Position position;
    private String email;

    private Employee currentEmployee;

    private EmployeeService employeeService;
    private UserService userService;

    private Helper helper;
    private UserSession session;

    /* Loading of Employees list */
    public void initialize() {
        employeeService = ApplicationContextFactory.getApplicationContext().getBean("employeeService", EmployeeService.class);
        userService = ApplicationContextFactory.getApplicationContext().getBean("userService", UserService.class);

        // has supplementary methods
        helper = new Helper();

        // set columns in TableView
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        // fill TableView with Employees from DB
        employees = FXCollections.observableArrayList(employeeService.findAll());
        employeesTable.setItems(employees);

        // set default items for ComboBoxes
        sexBox.setItems(FXCollections.observableArrayList(Sex.values()));
        positionBox.setItems(FXCollections.observableArrayList(Position.values()));

        // Save current Employee
        // change info about Employee when selected item in the TableView is changed
        helper.addSelectListener();
        // add Button listeners for changing TextFields & ComboBoxes
        // it activates Apply / Add button
        helper.addInvalidationListenerFor(applyButton);
        helper.addInvalidationListenerFor(addButton);
    }

    /* Action for Create button click */
    @FXML
    public void create() throws IOException {
        helper.fillFieldsWith(null);
        idField.setText("will be generated");
        sexBox.setPromptText("Choose one");
        positionBox.setPromptText("Choose one");

        //generateButton.setVisible(true);
        //generateButton.setDisable(true);

        noAccount.setVisible(false);
        withAccount.setVisible(false);
        accountInformation.setVisible(false);
        separatorAbove.setVisible(false);
        separatorUnder.setVisible(false);

        helper.disableAnother(true, createButton, addButton, cancelCreatingButton);
        helper.openFields(true);
    }

    // Adds new Employee to DB
    @FXML
    public void add() throws IOException {
        helper.validateFields();

        if (name != null && surname != null && age != null && sex != null && position != null && email != null) {
            Employee employee = new Employee(name, surname, age, sex, position, email);

            employeeService.create(employee);
            employees.add(employee);
            employeesTable.getSelectionModel().select(employee);

            noAccount.setVisible(true);
            withAccount.setVisible(true);
            accountInformation.setVisible(true);
            separatorAbove.setVisible(true);
            separatorUnder.setVisible(true);

            helper.fillFieldsWith(currentEmployee);

            helper.disableAnother(false, createButton, addButton, cancelCreatingButton);
            helper.openFields(false);
        }
    }

    @FXML
    public void cancelCreating() {
        helper.fillFieldsWith(currentEmployee);
        //noAccount.setVisible(true);
        //withAccount.setVisible(true);
        accountInformation.setVisible(true);
        separatorAbove.setVisible(true);
        separatorUnder.setVisible(true);

        helper.disableAnother(false, createButton, addButton, cancelCreatingButton);
        helper.openFields(false);
    }

    /* Action for Change button click
    *  It provides the ability to change info about Employee */
    @FXML
    public void change() throws IOException {

        if (currentEmployee == null) return;

        // makes all TextFields and ComboBoxes editable
        helper.disableAnother(true, changeButton, applyButton, cancelChangingButton);
        helper.openFields(true);
    }

    /* Action for Apply button click
     * Provides saving changes
     * Also Cancel button becomes available to revert changes */
    @FXML
    public void apply() {

        // validate TextFields
        helper.validateFields();

        // If Fields are correct, apply changes
        if (name != null && surname != null && age != null && email != null) {
            if (!name.equals(currentEmployee.getName()))            currentEmployee.setName(name);
            if (!surname.equals(currentEmployee.getSurname()))      currentEmployee.setSurname(surname);
            if (!age.equals(currentEmployee.getAge()))              currentEmployee.setAge(age);
            if (!sex.equals(currentEmployee.getSex()))              currentEmployee.setSex(sex);
            if (!position.equals(currentEmployee.getPosition()))    currentEmployee.setPosition(position);
            if (!email.equals(currentEmployee.getEmail()))          currentEmployee.setEmail(email);

            helper.disableAnother(false, changeButton, applyButton, cancelChangingButton);
            helper.openFields(false);

            employeeService.update(currentEmployee);
        }
    }

    // Action for Cancel button click
    @FXML
    public void cancelChanging() {
        helper.fillFieldsWith(currentEmployee);

        helper.disableAnother(false, changeButton, applyButton, cancelChangingButton);
        helper.openFields(false);

    }

    /* Action for Generate button click
    *  Generates User account for selected Employee
    * */
    @FXML
    public void generate() {
        // Check if account doesn't exist
        // if so then create acc and addButton it to DB
        if (currentEmployee.getUser() == null) {
            String name = currentEmployee.getName();
            String surname = currentEmployee.getSurname();
            String login = LoginHelper.generate(name, surname);
            User user = new User(login, LoginHelper.generatePassword(), currentEmployee);

            EmailSender.newUser(user);

            userService.create(user);
            currentEmployee.setUser(user);
            employeeService.update(currentEmployee);

            // User account has been generated,
            // so make the button invisible
            //generateButton.setVisible(false);
            noAccount.setVisible(false);
            withAccount.setVisible(true);
            helper.fillFieldsWith(currentEmployee);
            //userLogin.setVisible(true);
            //userLogin.setText(login);

        }
    }

    /* Action for Delete button click */
    @FXML
    public void delete() {
        if (currentEmployee != null) {
            helper.disableAnother(true, deleteButton, applyDeletingButton, cancelDeletingButton);
            applyDeletingButton.setDisable(false);
            cancelDeletingButton.requestFocus();
        }
    }

    @FXML
    public void applyDeleting() {
        User user;
        if ((user = currentEmployee.getUser()) != null) {
            // Delete dependency on User
            currentEmployee.setUser(null);
            employeeService.update(currentEmployee);
            // Safely remove user
            // we can do this, because employee
            // does not depend on it
            userService.delete(user);
        }
        // then delete employee
        employeeService.delete(currentEmployee);
        // refresh TableView
        employeesTable.getItems().remove(currentEmployee);

        helper.disableAnother(false, deleteButton, applyDeletingButton, cancelDeletingButton);
    }

    @FXML
    public void cancelDeleting() {
        helper.disableAnother(false, deleteButton, applyDeletingButton, cancelDeletingButton);
    }

    @Override
    public void setUserSession(UserSession session) {
        this.session = session;
    }

    /* contains all supplementary methods */
    private class Helper {
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
                loginField.setText("");
                passwordField.setText("");
                emailField.setText("");
                // Boxes
                sexBox.setPromptText("");
                sexBox.setValue(null);
                positionBox.setPromptText("");
                positionBox.setValue(null);
                // 'Has account?'
                generateButton.setVisible(false);
                //userLogin.setVisible(false);

                return;
            }

            // Fill TextField & ComboBoxes with employee details
            idField.setText(employee.getId().toString());
            nameField.setText(employee.getName());
            surnameField.setText(employee.getSurname());
            ageField.setText(employee.getAge().toString());
            emailField.setText(employee.getEmail());
            sexBox.setValue(employee.getSex());
            positionBox.setValue(employee.getPosition());

            if(employee.getUser() != null) {
                noAccount.setVisible(false);
                withAccount.setVisible(true);
                loginField.setText(employee.getUser().getLogin());
                passwordField.setText(employee.getUser().getPassword());
            } else {
                noAccount.setVisible(true);
                withAccount.setVisible(false);
            }

            // Special case for field 'Has account?'
            User user;
            Boolean userExists = ((user = employee.getUser()) != null);
            generateButton.setVisible(!userExists);
            generateButton.setDisable(false);
            //userLogin.setVisible(userExists);
            //if (userExists) userLogin.setText(user.getLogin());
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
            emailField.setDisable(bool);
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
        private void addInvalidationListenerFor(Button button) {
            InvalidationListener listener = (observable) -> {
                if (!nameField.getText().isEmpty() &&
                        !surnameField.getText().isEmpty() &&
                        !ageField.getText().isEmpty() &&
                        sexBox.getValue() != null &&
                        positionBox.getValue() != null &&
                        !emailField.getText().isEmpty()) {
                    button.setDisable(false);
                } else {
                    button.setDisable(true);
                }
            };
            nameField.textProperty().addListener(listener);
            surnameField.textProperty().addListener(listener);
            ageField.textProperty().addListener(listener);
            sexBox.valueProperty().addListener(listener);
            positionBox.valueProperty().addListener(listener);
            emailField.textProperty().addListener(listener);
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

        private void validateFields() {
            name = InputDataChecker.checkString(nameField);
            surname = InputDataChecker.checkString(surnameField);
            age = InputDataChecker.checkInteger(ageField);
            sex = InputDataChecker.checkEnum(sexBox);
            position = InputDataChecker.checkEnum(positionBox);
            email = InputDataChecker.checkEmail(emailField);
        }
    }

    @FXML
    public void logOut() {
        StageFactory.backToLogInWindow();
    }

    // Menu
    @FXML
    public void changePassword() {
        StageFactory.loadModal("/view/modal/change_password.fxml", "Change password", session);
    }
}
