package controller;

import entity.Employee;
import entity.User;
import enum_types.Position;
import enum_types.Sex;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import util.HibernateSessionFactory;

public class CreateController {

    @FXML
    public TextField fldName;

    @FXML
    public TextField fldSurname;

    @FXML
    public TextField fldAge;

    @FXML
    public TextField fldSex;

    @FXML
    public TextField fldPosition;

    @FXML
    public Button btnCreate;

    @FXML
    public Button btnClose;

    @FXML
    public void actionCloseButton() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void actionCreateButton() {

        UserService userService = new UserServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

        Employee employee = new Employee();
        employee.setName(fldName.getText());
        employee.setSurname(fldSurname.getText());
        employee.setAge(Integer.parseInt(fldAge.getText()));
        if (fldSex.getText().equals("male")) {
            employee.setSex(Sex.MALE);
        } else if (fldSex.getText().equals("female")) {
            employee.setSex(Sex.FEMALE);
        }
        employee.setPosition(Position.ADMIN);

        User user = new User();
        user.setLogin(fldName.getText() + "." + fldSurname.getText());
        user.setPassword("qwerty");
        user.setEmployee(employee);

        employeeService.add(employee);
        userService.add(user);

        HibernateSessionFactory.getSessionFactory().close();

        Stage stage = (Stage) btnCreate.getScene().getWindow();
        stage.close();
    }

}
