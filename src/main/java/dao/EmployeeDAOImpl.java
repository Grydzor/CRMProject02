package dao;

import entity.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

import java.util.List;

public class EmployeeDAOImpl extends DAOImpl<Employee> implements EmployeeDAO {
    private SessionFactory factory;

    public EmployeeDAOImpl() {
        super(Employee.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }
}
