package controller.modal;

import entity.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.context.ApplicationContext;
import service.CustomerService;
import util.ApplicationContextFactory;
import util.InputDataChecker;
import util.StageFactory;

public class NewCustomerController implements ModalController<Object, Customer> {
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField mobileField;
    @FXML private TextField emailField;

    private Customer customer;
    private ApplicationContext context;

    public void initialize() {
        context = ApplicationContextFactory.getApplicationContext();
    }

    @FXML
    public void save() {
        String name = InputDataChecker.checkString(nameField);
        String surname = InputDataChecker.checkString(surnameField);
        String mobile = InputDataChecker.checkMobile(mobileField);
        String email = InputDataChecker.checkEmail(emailField);

        if (name != null && surname != null && mobile != null && email != null) {
            customer = context.getBean(Customer.class);
            customer.setName(name);
            customer.setSurname(surname);
            customer.setMobile(mobile);
            customer.setEmail(email);
            CustomerService customerService = (CustomerService) context.getBean("customerService");
            customerService.create(customer);

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
