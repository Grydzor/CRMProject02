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
import javafx.scene.layout.HBox;
import service.EmployeeService;
import service.UserService;
import util.*;

/**
 * Created by Никита on 14.03.2017.
 * Финальная версия
 */

public class AdminController2 implements MainController {

    @FXML private Button addUserButton;
    @FXML private Button editUserButton;
    @FXML private Button deleteUserButton;
    @FXML private Button applyButton;
    @FXML private Button cancelButton;
    @FXML private Button logOutButton;
    @FXML private Button nextStepButton;
    @FXML private Button refreshButton;

    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField emailField;
    @FXML private TextField ageField;
    @FXML private TextField loginField;
    @FXML private TextField passwordField;

    @FXML private ComboBox<Sex> sexComboBox;
    @FXML private ComboBox<Position> positionComboBox;

    @FXML private HBox personalInformationBox;
    @FXML private HBox accountSettingsBox;
    @FXML private HBox loginAndPasswordBox;

    @FXML private TableView<Employee> tableView;
    @FXML private TableColumn<Employee, Long> idColumn;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> surnameColumn;
    @FXML private TableColumn<Employee, Sex> genderColumn;
    @FXML private TableColumn<Employee, Integer> ageColumn;
    @FXML private TableColumn<Employee, Position> positionColumn;
    @FXML private TableColumn<Employee, String> emailColumn;
    @FXML private TableColumn<Employee, String> accountColumn;
    private Employee currentEmployee;
    private User currentUser;

    private String statusOfApply;

    private UserSession session;

    private EmployeeService employeeService = ApplicationContextFactory.getApplicationContext().getBean("employeeService", EmployeeService.class);
    private UserService userService = ApplicationContextFactory.getApplicationContext().getBean("userService", UserService.class);

    public void initialize() {
        fillTableViewAndBoxes();
        addListenerTableView();
        addListenerNewStepButton();
        addListenerApplyButton();
    }

    @FXML
    public void addUserButtonOnAction() {
        tableView.setVisible(false);
        personalInformationBox.setVisible(true);
        disableButtonBarButtons();
        applyButton.setVisible(true);
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);
        currentEmployee = null;
        currentUser = null;
        statusOfApply = "add";
    }

    @FXML
    public void editUserButtonOnAction() {
        fillFields();
        tableView.setVisible(false);
        personalInformationBox.setVisible(true);
        disableButtonBarButtons();
        applyButton.setVisible(true);
        cancelButton.setVisible(true);
        cancelButton.setDisable(false);
        statusOfApply = "update";
    }

    @FXML
    public void deleteUserButtonOnAction() {
        tableView.setDisable(true);
        disableButtonBarButtons();
        applyButton.setVisible(true);
        cancelButton.setVisible(true);
        applyButton.setDisable(false);
        cancelButton.setDisable(false);
        statusOfApply = "delete";
    }

    @FXML
    public void applyButtonOnAction() {
        switch (statusOfApply) {
            case "add":
                if (userWillBeCreated()) {
                    currentUser = new User(loginField.getText(), passwordField.getText(), null);
                    userService.create(currentUser);
                    currentEmployee.setUser(currentUser);
                }
                employeeService.create(currentEmployee);
                if (userWillBeCreated()) {
                    currentEmployee.getUser().setEmployee(currentEmployee);
                    userService.update(currentEmployee.getUser());
                    EmailSender.newUser(currentUser);
                }
                currentUser = null;
                currentEmployee = null;
                tableView.setVisible(true);
                accountSettingsBox.setVisible(false);
                applyButton.setVisible(false);
                cancelButton.setVisible(false);
                loginField.setEditable(true);
                passwordField.setEditable(true);
                clearFields();
                activateButtonBarButtons();
                refreshTable();
                break;
            case "delete":
                if (currentEmployee.getUser() != null) {
                    User user = currentEmployee.getUser();
                    currentEmployee.setUser(null);
                    employeeService.update(currentEmployee);
                    userService.delete(user);
                }
                employeeService.delete(currentEmployee);
                tableView.getItems().remove(currentEmployee);
                tableView.setDisable(false);
                activateButtonBarButtons();
                applyButton.setVisible(false);
                cancelButton.setVisible(false);
                applyButton.setDisable(true);
                cancelButton.setDisable(true);
                refreshTable();
                break;
            case "update":
                tableView.setVisible(true);
                accountSettingsBox.setVisible(false);
                applyButton.setVisible(false);
                cancelButton.setVisible(false);
                loginField.setEditable(true);
                passwordField.setEditable(true);
                clearFields();
                activateButtonBarButtons();
                currentUser = null;
                currentEmployee = null;
                refreshTable();
                break;
        }
    }

    @FXML
    public void cancelButtonOnAction() {
        tableView.setVisible(true);
        tableView.setDisable(false);
        personalInformationBox.setVisible(false);
        accountSettingsBox.setVisible(false);
        activateButtonBarButtons();
        applyButton.setVisible(false);
        cancelButton.setVisible(false);
        applyButton.setDisable(true);
        cancelButton.setDisable(true);
        tableView.getSelectionModel().select(null);
    }

    @FXML
    public void nextStepButtonOnAction() {
        if (currentEmployee != null) {
            currentEmployee.setName(nameField.getText());
            currentEmployee.setSurname(surnameField.getText());
            currentEmployee.setAge(Integer.valueOf(ageField.getText()));
            currentEmployee.setEmail(emailField.getText());
            currentEmployee.setSex(sexComboBox.getValue());
            currentEmployee.setPosition(positionComboBox.getValue());
            employeeService.update(currentEmployee);
        } else {
            currentEmployee = new Employee(nameField.getText(), surnameField.getText(), Integer.valueOf(ageField.getText()), sexComboBox.getValue(), positionComboBox.getValue(), emailField.getText());
        }
        personalInformationBox.setVisible(false);
        accountSettingsBox.setVisible(true);
    }

    @FXML
    public void autoButtonOnAction() {
        loginAndPasswordBox.setVisible(true);
        if (currentEmployee.getUser() == null) {
            currentUser = new User(LoginHelper.generate(currentEmployee.getName(), currentEmployee.getSurname()), LoginHelper.generatePassword(), null);
            userService.create(currentUser);
            currentEmployee.setUser(currentUser);
            loginField.setText(currentEmployee.getUser().getLogin());
            passwordField.setText(currentEmployee.getUser().getPassword());
            loginField.setEditable(false);
            passwordField.setEditable(false);
        } else if (currentEmployee.getUser() != null) {
            currentEmployee.getUser().setLogin(LoginHelper.generate(currentEmployee.getName(), currentEmployee.getSurname()));
            currentEmployee.getUser().setPassword(LoginHelper.generatePassword());
            userService.update(currentEmployee.getUser());
            loginField.setText(currentEmployee.getUser().getLogin());
            passwordField.setText(currentEmployee.getUser().getPassword());
            loginField.setEditable(false);
            passwordField.setEditable(false);
        }
    }

    @FXML
    public void manualButtonOnAction() {
        if (currentEmployee.getUser() != null) {
            loginAndPasswordBox.setVisible(true);
            loginField.setEditable(true);
            passwordField.setEditable(true);
        }
    }

    @FXML
    public void noAccountButtonOnAction() {
        if (currentEmployee.getUser() != null) {
            currentEmployee.setUser(null);
        }
        applyButton.setDisable(false);
    }

    @FXML
    public void changePasswordButtonOnAction() {
        StageFactory.loadModal("/view/modal/change_password.fxml", "Change password", session);
    }

    @FXML
    public void refreshButtonOnAction() {
        refreshTable();
    }

    @FXML
    public void logOutButtonOnAction() {
        StageFactory.backToLogInWindow();
    }

    @Override
    public void setUserSession(UserSession session) {
        this.session = session;
    }

    private void disableButtonBarButtons() {
        addUserButton.setDisable(true);
        editUserButton.setDisable(true);
        deleteUserButton.setDisable(true);
        refreshButton.setDisable(true);
        logOutButton.setDisable(true);
    }

    private void activateButtonBarButtons() {
        addUserButton.setDisable(false);
        editUserButton.setDisable(false);
        deleteUserButton.setDisable(false);
        refreshButton.setDisable(false);
        logOutButton.setDisable(false);
    }

    private void refreshTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        ObservableList<Employee> employees = FXCollections.observableArrayList(employeeService.findAll());
        tableView.setItems(employees);
        tableView.getSelectionModel().select(null);
    }

    private void fillTableViewAndBoxes() {
        refreshTable();
        tableView.getSelectionModel().select(null);
        sexComboBox.setItems(FXCollections.observableArrayList(Sex.values()));
        positionComboBox.setItems(FXCollections.observableArrayList(Position.values()));
    }

    private void addListenerTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    currentEmployee = newValue;
                    editUserButton.setDisable(currentEmployee == null);
                    deleteUserButton.setDisable(currentEmployee == null);
                });
    }

    private void addListenerNewStepButton() {
        InvalidationListener listener = observable -> {
            if (isUserFieldsFilled()) {
                nextStepButton.setDisable(false);
            } else {
                nextStepButton.setDisable(true);
            }
        };
        nameField.textProperty().addListener(listener);
        surnameField.textProperty().addListener(listener);
        emailField.textProperty().addListener(listener);
        ageField.textProperty().addListener(listener);
        sexComboBox.valueProperty().addListener(listener);
        positionComboBox.valueProperty().addListener(listener);
    }

    private void addListenerApplyButton() {
        InvalidationListener listener = observable -> {
            if (isAccountFieldsFilled()) {
                applyButton.setDisable(false);
            } else {
                applyButton.setDisable(true);
            }
        };
        loginField.textProperty().addListener(listener);
        passwordField.textProperty().addListener(listener);
    }

    private void fillFields() {
        nameField.setText(currentEmployee.getName());
        surnameField.setText(currentEmployee.getSurname());
        ageField.setText(String.valueOf(currentEmployee.getAge()));
        emailField.setText(currentEmployee.getEmail());
        sexComboBox.setValue(currentEmployee.getSex());
        positionComboBox.setValue(currentEmployee.getPosition());
        if (currentEmployee.getUser() != null) {
            loginField.setText(currentEmployee.getUser().getLogin());
            passwordField.setText(currentEmployee.getUser().getPassword());
        }
    }

    private void clearFields() {
        nameField.setText("");
        surnameField.setText("");
        ageField.setText("");
        emailField.setText("");
        sexComboBox.setValue(null);
        positionComboBox.setValue(null);
        loginField.setText("");
        passwordField.setText("");
    }

    private boolean userWillBeCreated() {
        return currentEmployee.getUser() == null && !loginField.getText().equals("") && !passwordField.getText().equals("");
    }

    private boolean isUserFieldsFilled() {
        return !nameField.getText().isEmpty() && !surnameField.getText().isEmpty() && !emailField.getText().isEmpty() && !ageField.getText().isEmpty() && sexComboBox.getValue() != null && positionComboBox.getValue() != null;
    }

    private boolean isAccountFieldsFilled() {
        return !loginField.getText().isEmpty() && !passwordField.getText().isEmpty();
    }

}
