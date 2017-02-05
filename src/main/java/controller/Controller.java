package controller;

import service.EmployeeService;
import service.UserService;

/**
 * Created by eriol4ik on 05/02/2017.
 */
public interface Controller {
    public void setService(EmployeeService service);
    public void setService(UserService service);
}
