package controller;

import entity.Employee;
import enum_types.Position;
import enum_types.Sex;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.GraphicsLoader;
import util.InputDataChecker;

public class CreateController {
    @FXML private TextField fldName;
    @FXML private TextField fldSurname;
    @FXML private TextField fldAge;
    @FXML private ComboBox<Sex> boxSex;
    @FXML private ComboBox<Position> boxPosition;

    @FXML private Button btnCreate;
    @FXML private Button btnClose;

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    @FXML
    public void initialize() {
        boxSex.setItems(FXCollections.observableArrayList(Sex.values()));
        boxPosition.setItems(FXCollections.observableArrayList(Position.values()));
    }

    @FXML
    public void actionCloseButton() {
        GraphicsLoader.closeWindow(fldName);
    }

    @FXML
    public void actionCreateButton() {
        String name         =   InputDataChecker.checkString(fldName);
        String surname      =   InputDataChecker.checkString(fldSurname);
        Integer age         =   InputDataChecker.checkInteger(fldAge);
        Sex sex             =   InputDataChecker.checkEnum(boxSex);
        Position position   =   InputDataChecker.checkEnum(boxPosition);

        if (name != null && surname != null && age != null
                && sex != null && position != null) {

            employee = new Employee(name, surname, age, sex, position);

            GraphicsLoader.closeWindow(fldName);
        }
    }

}
