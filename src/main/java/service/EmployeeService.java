package service;

import entity.Employee;
import entity.User;

import java.util.List;

public interface EmployeeService extends Service<Employee> {
    User find(String email);
}
