package controller;

import service.EmployeeService;
import service.UserService;

public interface Controller {
    void setService(EmployeeService service);

    void setService(UserService service);
}
