package web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.entity.Customer;
import web.entity.CustomerAccount;
import web.entity.Product;
import web.form.LoginData;
import web.form.RegisterData;
import web.service.CustomerAccountService;
import web.service.CustomerService;
import web.service.ProductService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class JsonController {
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Set<String> json() {
        Set<String> records = new HashSet<>();
        records.add("Record #1");
        records.add("Record #2");

        return records;
    }

    @RequestMapping(value = "/jsonlogin", method = RequestMethod.POST)
    @ResponseBody
    public String jsonlogin(@RequestBody String json) {
        Gson gsonIn = new Gson();
        LoginData data = gsonIn.fromJson(json, LoginData.class);

        JsonObject jsonObject = new JsonObject();

        Customer customer = customerService.find(data.getEmail());

        if (customer == null) {
            jsonObject.addProperty("answer", false);
            return jsonObject.toString();
        }
        CustomerAccount customerAccount = customer.getAccount();

        if (customerAccount != null && data.getPassword().equals(customerAccount.getPassword())) {
            jsonObject.addProperty("answer", true);
            return jsonObject.toString();
        }

        jsonObject.addProperty("answer", false);
        return jsonObject.toString();
    }

    @RequestMapping(value = "/jsonreg", method = RequestMethod.POST)
    @ResponseBody
    public String jsonreg(@RequestBody String json) {
        Gson gsonIn = new Gson();
        RegisterData data = gsonIn.fromJson(json, RegisterData.class);

        JsonObject jsonObject = new JsonObject();

        CustomerAccount customerAccount = context.getBean(CustomerAccount.class);
        customerAccount.setEmail(data.getEmail());
        customerAccount.setPassword(data.getPassword());
        CustomerAccountService customerAccountService = context.getBean(CustomerAccountService.class);
        customerAccountService.create(customerAccount);

        Customer customer = context.getBean(Customer.class);
        customer.setName(data.getName());
        customer.setSurname(data.getSurname());
        customer.setMobile(data.getPhone());
        customer.setAddress(data.getAddress());
        customer.setAccount(customerAccount);
        CustomerService customerService = context.getBean(CustomerService.class);
        customerService.create(customer);

        if (customer.getId() == null){
            jsonObject.addProperty("answer", false);
            return jsonObject.toString();
        }

        if (customer.getId() != null) {
            jsonObject.addProperty("answer", true);
            return jsonObject.toString();
        } else {
            jsonObject.addProperty("answer", false);
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/jsonproduct", method = RequestMethod.GET)
    @ResponseBody
    public String jsonproduct() {
        ProductService productService = context.getBean(ProductService.class);
        List<Product> listproducts = productService.findAll();

        Gson gson = new GsonBuilder().create();
        String productsJson = gson.toJson(listproducts);
        return productsJson;
    }
}
