package dao;

import entity.Employee;
import org.springframework.stereotype.Repository;

@Repository("employeeDAO")
public class EmployeeDAOImpl extends DAOImpl<Employee> implements EmployeeDAO {


    protected EmployeeDAOImpl() {
        super(Employee.class);
    }


}
