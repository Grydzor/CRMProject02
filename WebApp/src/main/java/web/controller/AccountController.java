package web.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.WebRequest;
import web.entity.Customer;
import web.entity.Item;
import web.entity.Order;
import web.form.ItemData;
import web.form.LoginData;
import org.springframework.web.bind.support.SessionStatus;
import web.form.OrderData;
import web.service.*;

import javax.persistence.PostRemove;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes(types = {Order.class, Customer.class})
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
            model.addAttribute("customer", customerService.find(data.getEmail()));
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
        Item item = new Item(productService.readWithPictures(Long.parseLong(data.getId())), Integer.parseInt(data.getQty()), order);
        order.getItems().add(item);
        return "embedded/elements/inCart";
    }

    @RequestMapping(value = "/updateorderdata", method = RequestMethod.POST)
    public @ResponseBody String updateOrderData(Model model, Order order) {
        JsonObject object = new JsonObject();

        System.out.print("here");
        System.out.println(order);

        object.addProperty("summary", order.getSummaryFormat());
        object.addProperty("amount", order.getAmount());

        System.out.println(object.toString());
        return object.toString();
    }

    @RequestMapping(value = "/submitorder", method = RequestMethod.POST)
    public String submitOrder(@RequestBody OrderData orderData,
                           HttpSession session,
                           Model model,
                           Customer customer,
                           Order order) {
        if (customer.getId() == null) {
            customer = new Customer();
            customer.setMobile(orderData.getName());
            customer.setName(orderData.getName());
            customerService.create(customer);
        }

        order.setCustomer(customer);
        orderService.create(order);

        model.addAttribute("order", new Order());

//        session.removeAttribute("order");

        if (!"".equals(orderData.getCity()) && !"".equals(orderData.getStreet()) && !"".equals(orderData.getHouseNumber())) {
            model.addAttribute("containsAddress", true);
        }
        if (!"".equals(orderData.getEmail()) && !"".equals(orderData.getPassword()) && !"".equals(orderData.getConfirmPassword())) {
            if (orderData.getPassword().equals(orderData.getConfirmPassword())) {
                model.addAttribute("containsRegData", true);
            }
        }
        return "embedded/submitted_order";
    }
}
