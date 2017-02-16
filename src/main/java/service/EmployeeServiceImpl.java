package service;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import entity.Employee;

import java.util.List;

public class EmployeeServiceImpl extends ServiceImpl<Employee> implements EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl() {
        super(Employee.class);
        employeeDAO = new EmployeeDAOImpl();
    }
}
