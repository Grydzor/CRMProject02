package service;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import entity.Employee;

import java.util.List;

public class EmployeeServiceImpl extends ServiceImpl<Employee> implements EmployeeService {
    private EmployeeDAO employeeDAO;
    private static EmployeeServiceImpl singleton;

    private EmployeeServiceImpl() {
        super(Employee.class);
        employeeDAO = EmployeeDAOImpl.getInstance();
    }

    public static EmployeeServiceImpl getInstance() {
        if (singleton == null) singleton = new EmployeeServiceImpl();
        return singleton;
    }
}
