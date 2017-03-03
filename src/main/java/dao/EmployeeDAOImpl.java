package dao;

import entity.Employee;
import entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeDAO")
public class EmployeeDAOImpl extends DAOImpl<Employee> implements EmployeeDAO {
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected EmployeeDAOImpl() {}

    @Override
    public User find(String email) {
        Query<User> query = factory.getCurrentSession()
                .createQuery("SELECT e.user FROM Employee e where e.email = :email", User.class);
        query.setParameter("email", email);
        List<User> list = query.list();
        return list.isEmpty() ? null : list.get(0);
    }
}
