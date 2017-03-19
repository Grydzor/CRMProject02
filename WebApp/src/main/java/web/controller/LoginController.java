package web.controller;

import entity.Item;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import service.CustomerService;
import entity.Customer;
import entity.CustomerAccount;

import org.springframework.stereotype.Controller;
import service.ProductService;
import web.form.LoginData;
import service.CustomerAccountService;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(types = {Customer.class})
public class LoginController {
    @Autowired
    ApplicationContext context;
    @Autowired
    CustomerAccountService customerAccountService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private String login(@RequestBody LoginData data, Model model) {
        Customer customer = customerService.find(data.getEmail());
//        this.customer = customer;
        CustomerAccount customerAccount = customerAccountService.read(customer.getId());

        if (customerAccount != null && data.getPassword().equals(customerAccount.getPassword())) {
            System.out.println("here");
            model.addAttribute(customer);
            System.out.println("here");
            return "embedded-jsp/user-form";
        }
        return "";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
//        customer = null;
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

    @RequestMapping(value = "/addtocart", method = RequestMethod.GET)
    public @ResponseBody String addToCart(Model model, Order order,
                            @RequestParam Long productId,
                            @RequestParam Integer amount) {
        if (order == null) {
            model.addAttribute(order = new Order());
            order.setItems(new ArrayList<>());
        }

        Item item = new Item(productService.read(productId), amount, order);
        order.getItems().add(item);
        return "ok";
        //todo Написати для цього метода запит в контроллері
    }
}
