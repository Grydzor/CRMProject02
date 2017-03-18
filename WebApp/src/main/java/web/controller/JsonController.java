package web.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import entity.Customer;
import entity.CustomerAccount;
import web.form.LoginData;
import service.CustomerAccountService;
import service.CustomerService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class JsonController {
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Set<String> json() {
        Set<String> records = new HashSet<>();
        records.add("Record #1");
        records.add("Record #2");

        return records;
    }

    @RequestMapping(value = "/jsonlogin", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public String jsonlogin(@RequestBody LoginData data) {

        if(data == null){return "false";}
        Customer customer = customerService.find(data.getEmail());
        CustomerAccount customerAccount = customerAccountService.read(customer.getId());

        if (customerAccount != null && data.getPassword().equals(customerAccount.getPassword())) {
            return "true";
        }
        return "false";
    }
}
