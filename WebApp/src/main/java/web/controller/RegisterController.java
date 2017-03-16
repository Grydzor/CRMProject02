package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.entity.Customer;
import web.entity.CustomerAccount;
import web.service.CustomerService;

@Controller
public class RegisterController {
    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/finishReg", method = RequestMethod.POST)
    private @ResponseBody String submitReg(
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            @RequestParam("confirmPassword") String confirmPassword,
                            @RequestParam("name") String name,
                            @RequestParam("surname") String surname,
                            @RequestParam("phone") String phone,
                            @RequestParam("address") String address) {
        Customer customer = context.getBean(Customer.class);
        customer.setEmail(email);
        customer.setName(name);
        customer.setSurname(surname);
        customer.setMobile(phone);
        customer.setAddress(address);
        CustomerService customerService = context.getBean(CustomerService.class);
        customerService.create(customer);

        CustomerAccount customerAccount = context.getBean(CustomerAccount.class);
        customerAccount.setCustomerId(customer.getId());
        customerAccount.setPassword(password);

        if (customer.getId() != null) {
            return "<h2>" +
                    "You are successfully registered! Please check your email." +
                    "</h2>";
        } else {
            return "<h2>" +
                    "Error during registration." +
                    "</h2>";
        }
    }
}
