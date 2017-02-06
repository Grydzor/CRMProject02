package dao;

import entity.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

import java.util.List;

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
        Session session = factory.openSession();
        try {
            return (Employee) session.get(Employee.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean update(Employee employee) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean delete(Employee employee) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.delete(employee);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findAll() {
        return factory.openSession().createCriteria(Employee.class).list();
    }
}
