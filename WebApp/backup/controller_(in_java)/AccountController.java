package web.controller;

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
            CustomerService service = context.getBean(CustomerService.class);
            model.addAttribute("orders", service.findOrders(customer));
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
        return "index";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart() {
        return "cart";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkout(@RequestParam(required = false) String mobile,
                           @RequestParam(required = false) String name,
                           Model model,
                           Customer customer,
                           Order order) {
        OrderService service = context.getBean(OrderService.class);
        ItemService itemService = context.getBean(ItemService.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        if (!model.containsAttribute("customer")) {
            customer = new Customer();
            customer.setMobile(mobile);
            customer.setName(name);
        }
        customerService.create(customer);
        order.setCustomer(customer);
        service.create(order);
        for (Item item : order.getItems()) {
            itemService.create(item);
        }
        return "checkout";
    }
}
