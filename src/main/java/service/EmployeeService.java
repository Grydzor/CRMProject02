package service;

import entity.Employee;

import java.util.List;

public interface EmployeeService {
    Long add(Employee employee);

    Employee find(Long id);

    Boolean update(Employee employee);

    Boolean delete(Employee employee);

    List<Employee> findAll();

}
