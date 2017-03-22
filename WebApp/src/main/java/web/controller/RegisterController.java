package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;
import entity.Customer;
import entity.CustomerAccount;
import web.form.RegisterData;
import service.CustomerAccountService;

@Controller
public class RegisterController {
    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/finishReg", method = RequestMethod.POST)
    private @ResponseBody String finishReg(@RequestBody RegisterData data) {
        Customer customer = context.getBean(Customer.class);
        customer.setEmail(data.getEmail());
        customer.setName(data.getName());
        customer.setSurname(data.getSurname());
        customer.setMobile(data.getPhone());
        customer.setAddress(data.getAddress());
        CustomerService customerService = context.getBean(CustomerService.class);
        customerService.create(customer);

        CustomerAccount customerAccount = context.getBean(CustomerAccount.class);
        customerAccount.setCustomerId(customer.getId());
        customerAccount.setPassword(data.getPassword());
        CustomerAccountService customerAccountService = context.getBean(CustomerAccountService.class);
        customerAccountService.create(customerAccount);

        if (customer.getId() != null) {
            return "<h2>" +
                    "You are successfully registered! Please log in to your account" +
                    "</h2>";
        } else {
            return "<h2>" +
                    "Error during registration." +
                    "</h2>";
        }
    }
}
