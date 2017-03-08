package dao;

import entity.Employee;
import entity.User;

public interface EmployeeDAO extends DAO<Employee> {
    User find(String email);
}
