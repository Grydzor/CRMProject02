package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.entity.Product;
import web.service.ProductService;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/cart", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String cart() {
        return "cart";
    }

    @RequestMapping(value = "/checkout", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String checkout() {
        return "checkout";
    }

    @RequestMapping(value = "/shop", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String shop() {
        return "shop";
    }

    @RequestMapping(value = "/single-product", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String singleProduct() {
        return "single-product";
    }
}
