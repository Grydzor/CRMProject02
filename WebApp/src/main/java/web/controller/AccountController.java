package web.controller;

import entity.Item;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import service.*;
import entity.Customer;
import entity.CustomerAccount;

import org.springframework.stereotype.Controller;
import web.form.ItemData;
import web.form.LoginData;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(types = {Customer.class, Order.class})
public class AccountController {
    @Autowired
    ApplicationContext context;
    @Autowired
    CustomerAccountService customerAccountService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;

    private Order order;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private String login(@RequestBody LoginData data, Model model) {
        Customer customer = customerService.find(data.getEmail());
        CustomerAccount customerAccount = customerAccountService.read(customer.getId());

        if (customerAccount != null && data.getPassword().equals(customerAccount.getPassword())) {
            model.addAttribute(customer);
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
    private String addtocart(@RequestBody ItemData data,
                                          Model model) {
        if (order == null) {
            model.addAttribute(order = new Order());
            order.setItems(new ArrayList<>());
        }

        Item item = new Item(productService.read(Long.parseLong(data.getId())), Integer.parseInt(data.getQty()), order);
        order.getItems().add(item);
        return "index";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    private String cart() {
        return "cart";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    private String checkout(@RequestParam(required = false) String mobile,
                            @RequestParam(required = false) String name,
                            Customer customer) {
        OrderService service = context.getBean(OrderService.class);
        ItemService itemService = context.getBean(ItemService.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        if (customer == null) {
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
