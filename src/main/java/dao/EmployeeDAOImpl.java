package dao;

import entity.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

import java.util.List;

@Deprecated
public class EmployeeDAOImpl implements EmployeeDAO {
    private SessionFactory factory;

    public EmployeeDAOImpl() {
        factory = HibernateSessionFactory.getSessionFactory();
    }

    @Override
    public Long create(Employee employee) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Long id = (Long) session.save(employee);
            session.getTransaction().commit();
            return id;
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Employee read(Long id) {
        return null;
    }

    @Override
    public Boolean update(Employee employee) {
        return null;
    }

    @Override
    public Boolean delete(Employee employee) {
        return null;
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }
}
