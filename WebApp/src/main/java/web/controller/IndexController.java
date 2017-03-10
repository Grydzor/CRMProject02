package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.service.UserService;

@Controller
public class IndexController {
    @Autowired
    private UserService service;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "registration";
    }

    @RequestMapping(value = "/recover")
    public String recover() {
        return "recover";
    }

    @RequestMapping(value = "/backoffice")
    public String backoffice() {
        return "backoffice";
    }
}
