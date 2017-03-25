package web.dao;

import web.entity.Employee;
import web.entity.User;

public interface EmployeeDAO extends DAO<Employee, Long> {
    User find(String email);
}
