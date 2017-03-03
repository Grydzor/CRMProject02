package dao;

import entity.Employee;
import entity.User;

import java.util.List;

public interface EmployeeDAO extends DAO<Employee> {
    User find(String email);
}
