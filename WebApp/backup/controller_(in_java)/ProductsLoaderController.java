package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.entity.Product;
import web.service.ProductService;

import java.util.List;

@Controller
public class ProductsLoaderController {
    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String loadProducts(@RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "by", required = false) String order,
                               @RequestParam(value = "asc", required = false) String asc,
                               Model model) {
        if (page == null) page = 1;
        if (order == null) order = "id";
        if (asc == null) asc = "yes";

        int productsPerPage = 12;
        ProductService service = context.getBean(ProductService.class);
        List<Product> products = service.findInRange((page - 1) * productsPerPage, productsPerPage, order, "yes".equals(asc));
        model.addAttribute("products", products);
        model.addAttribute("page", page);

        String filter = "";
        if ("id".equals(order) && "yes".equals(asc)) {
            filter = "newest";
        }
        if ("price".equals(order) && "yes".equals(asc)) {
            filter = "cheap first";
        }
        if ("price".equals(order) && "no".equals(asc)) {
            filter = "expensive first";
        }
        if ("name".equals(order) && "yes".equals(asc)) {
            filter = "alphabet";
        }
        if ("name".equals(order) && "no".equals(asc)) {
            filter = "alphabet desc";
        }
        model.addAttribute("filter", filter);

        return "products-page";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "q", required = false) String query,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "by", required = false) String order,
                         @RequestParam(value = "asc", required = false) String asc,
                         Model model) {
        if (page == null) page = 1;
        if (order == null) order = "id";
        if (asc == null) asc = "yes";

        int productsPerPage = 12;
        ProductService service = context.getBean(ProductService.class);
        List<Product> products;
        if (query != null) {
            products = service.searchInRange(query, (page - 1) * productsPerPage, productsPerPage, order, "yes".equals(asc));
            model.addAttribute("query", query);
        } else {
            products = service.findInRange((page - 1) * productsPerPage, productsPerPage, order, "yes".equals(asc));
        }
        model.addAttribute("products", products);
        model.addAttribute("page", page);

        String filter = "";
        if ("id".equals(order) && "yes".equals(asc)) {
            filter = "newest";
        }
        if ("price".equals(order) && "yes".equals(asc)) {
            filter = "cheap first";
        }
        if ("price".equals(order) && "no".equals(asc)) {
            filter = "expensive first";
        }
        if ("name".equals(order) && "yes".equals(asc)) {
            filter = "alphabet";
        }
        if ("name".equals(order) && "no".equals(asc)) {
            filter = "alphabet desc";
        }
        model.addAttribute("filter", filter);

        return "search";
    }
}

