package service;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import util.ApplicationContextFactory;

@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<Employee> implements EmployeeService {
    @Autowired
    @Qualifier("employeeDAO")
    private EmployeeDAO employeeDAO;

    private EmployeeServiceImpl() {}
}
