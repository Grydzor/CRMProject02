package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.EmployeeDAO;
import web.entity.Employee;
import web.entity.User;

@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<Employee, Long> implements EmployeeService {
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
