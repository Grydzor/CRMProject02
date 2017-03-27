package web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import web.entity.Customer;
import web.entity.Item;
import web.entity.Order;
import web.form.ItemData;
import web.form.LoginData;
import org.springframework.web.bind.support.SessionStatus;
import web.service.*;

import java.util.ArrayList;

@Controller
@SessionAttributes(types = {Customer.class, Order.class})
public class AccountController {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody LoginData data, Model model) {
        String pass = customerAccountService.findPass(data.getEmail());

        if (data.getPassword().equals(pass)) {
            model.addAttribute(customerService.find(data.getEmail()));
            return "embedded-jsp/user-form";
        }
        return "";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "embedded-jsp/login-form";
    }

    @RequestMapping(value = "/backoffice", method = RequestMethod.GET)
    public String backoffice(Model model, Customer customer) {
        if (customer != null) {
            model.addAttribute("orders", customerService.findOrders(customer));
            return "backoffice";
        } else {
            return "unsignined";
        }
    }

    @RequestMapping(value = "/addtocart", method = RequestMethod.POST)
    public String addtocart(@RequestBody ItemData data,
                                         Model model,
                                         Order order) {
        if (!model.containsAttribute("order")) {
            model.addAttribute("order", new Order());
            order.setItems(new ArrayList<>());
        }

        Item item = new Item(productService.read(Long.parseLong(data.getId())), Integer.parseInt(data.getQty()), order);
        order.getItems().add(item);
        return "embedded/elements/inCart";
    }

    @RequestMapping(value = "/updateorderdata", method = RequestMethod.POST)
    public @ResponseBody String updateOrderData(Model model, Order order) {
        JsonObject object = new JsonObject();

        if (model.containsAttribute("order")) {
            object.addProperty("summary", order.getUpdatedSummary());
            object.addProperty("amount", order.getAmount());
        } else {
            object.addProperty("summary", "0");
            object.addProperty("amount", "0");
        }
        return object.toString();
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkout(@RequestParam(required = false) String mobile,
                           @RequestParam(required = false) String name,
                           Model model,
                           Customer customer,
                           Order order) {
        if (!model.containsAttribute("customer")) {
            customer = new Customer();
            customer.setMobile(mobile);
            customer.setName(name);
        }
        customerService.create(customer);
        order.setCustomer(customer);
        orderService.create(order);
        for (Item item : order.getItems()) {
            itemService.create(item);
        }
        return "checkout";
    }
}
