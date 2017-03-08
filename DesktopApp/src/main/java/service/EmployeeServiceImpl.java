package service;

import dao.EmployeeDAO;
import entity.Employee;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<Employee> implements EmployeeService {
    @Autowired
    @Qualifier("employeeDAO")
    private EmployeeDAO employeeDAO;

    private EmployeeServiceImpl() {}

    @Override
    @Transactional
    public User find(String email) {
        return employeeDAO.find(email);
    }
}
