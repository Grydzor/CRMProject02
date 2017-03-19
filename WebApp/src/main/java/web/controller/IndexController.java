package web.controller;

import entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import entity.Product;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;
import service.ProductService;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(Model model) {
        int productsPerPage = 12;
        ProductService service = context.getBean(ProductService.class);
        List<Product> products = service.findInRange(0, productsPerPage, "id", true);
        model.addAttribute("products", products);
        model.addAttribute("page", 1);
        model.addAttribute("filter", "newest");
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "registration";
    }

    @RequestMapping(value = "/recover")
    public String recover() {
        return "recover";
    }


}
