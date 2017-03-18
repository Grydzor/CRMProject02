package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import service.CustomerService;
import entity.Customer;
import entity.CustomerAccount;

import org.springframework.stereotype.Controller;
import web.form.LoginData;
import service.CustomerAccountService;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes(types = {Customer.class})
public class LoginController {
    @Autowired
    CustomerAccountService customerAccountService;
    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private String login(@RequestBody LoginData data, Model model) {
        Customer customer = customerService.find(data.getEmail());
        CustomerAccount customerAccount = customerAccountService.read(customer.getId());

        if (customerAccount != null && data.getPassword().equals(customerAccount.getPassword())) {
            System.out.println("here");
            model.addAttribute(customer);
            System.out.println("here");
            return "embedded-jsp/user-form";
        }
        return "";
    }

    @RequestMapping(value = "/test")
    public String test() {
        return "test";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "embedded-jsp/login-form";
    }
}
