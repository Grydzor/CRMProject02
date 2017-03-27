package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.entity.Customer;
import web.entity.CustomerAccount;
import web.form.RegisterData;
import web.service.CustomerAccountService;
import web.service.CustomerService;

@Controller
public class RegisterController {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/finishReg", method = RequestMethod.POST)
    private String finishReg(@RequestBody RegisterData data) {
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setEmail(data.getEmail());
        customerAccount.setPassword(data.getPassword());
        customerAccountService.create(customerAccount);

        Customer customer = new Customer();
        customer.setName(data.getName());
        customer.setSurname(data.getSurname());
        customer.setMobile(data.getPhone());
        customer.setAddress(data.getAddress());
        customer.setAccount(customerAccount);
        customerService.create(customer);

        if (customer.getId() != null) {
            return "embedded/elements/success_reg";
        } else {
            return "embedded/elements/error_reg";
        }
    }
}
