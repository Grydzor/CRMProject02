package service;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import entity.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl() {
        employeeDAO = new EmployeeDAOImpl();
    }

    @Override
    public Long add(Employee employee) {
        return employeeDAO.create(employee);
    }

    @Override
    public Employee find(Long id) {
        return employeeDAO.read(id);
    }

    @Override
    public Boolean update(Employee employee) {
        return employeeDAO.update(employee);
    }

    @Override
    public Boolean delete(Employee employee) {
        return employeeDAO.delete(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }
}
