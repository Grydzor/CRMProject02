package web.service;

import web.entity.Employee;
import web.entity.User;

public interface EmployeeService extends Service<Employee> {
    User find(String email);
}
