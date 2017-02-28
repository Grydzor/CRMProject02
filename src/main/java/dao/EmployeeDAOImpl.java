package dao;

import entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("employeeDAO")
public class EmployeeDAOImpl extends DAOImpl<Employee> implements EmployeeDAO {

    @Autowired
    protected EmployeeDAOImpl() {}
}
