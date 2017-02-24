package service;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("employeeService")
public class EmployeeServiceImpl extends EmployeeDAOImpl implements EmployeeService {
    @Autowired
    //@Qualifier("employeeDAO")
    private EmployeeDAO employeeDAO;

    private EmployeeServiceImpl() {
        super();
    }


}
