package service;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import entity.Employee;

public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDAO employeeDAO;

    public EmployeeServiceImpl() {
        employeeDAO = new EmployeeDAOImpl();
    }

    @Override
    public Long add(Employee employee) {
        return employeeDAO.create(employee);
    }
}
