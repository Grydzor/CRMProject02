package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.service.UserService;

@Controller
public class   HelloController {
    @Autowired
    private UserService service;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index() {
        System.out.println("--->>> CONTROLLER INDEX <<<---");
        return "index";
    }
}
