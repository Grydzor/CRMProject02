package controller.modal;

import entity.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.CustomerServiceImpl;
import util.InputDataChecker;
import util.StageFactory;

/**
 * Created by eriol4ik on 19.02.2017.
 */
public class NewCustomerController implements ValueSettable<Object, Customer> {
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField mobileField;
    @FXML private TextField emailField;

    private Customer customer;

    public void initialize() {

    }

    @FXML
    public void save() {
        String name = InputDataChecker.checkString(nameField);
        String surname = InputDataChecker.checkString(surnameField);
        String mobile = InputDataChecker.checkString(mobileField);
        String email = InputDataChecker.checkString(emailField);

        if (name != null && surname != null && mobile != null && email != null) {
            customer = new Customer(name, surname, mobile, email);
            CustomerServiceImpl.getInstance().add(customer);

            StageFactory.closeModal();
        }
    }

    @FXML
    public void cancel() {
        StageFactory.closeModal();
    }


    @Override
    public void setParameter(Object parameter) {
    }

    @Override
    public Customer getResult() {
        return customer;
    }
}
