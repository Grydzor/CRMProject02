package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import entity.Product;
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

    @RequestMapping(value = "/backoffice", method = RequestMethod.GET)
    public String backoffice() {
        return "backoffice";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search() {
        return "search";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String loadProducts(@RequestParam("page") Integer page, Model model) {
        int productsPerPage = 12;
        ProductService service = context.getBean(ProductService.class);
        List<Product> products = service.findInRange((page - 1) * productsPerPage, productsPerPage, "id", true);
        model.addAttribute("products", products);
        model.addAttribute("page", page);
        return "loadProducts";
    }
}
