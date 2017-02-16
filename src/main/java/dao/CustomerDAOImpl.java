package dao;

import entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class CustomerDAOImpl extends DAOImpl<Customer> implements CustomerDAO {
    private SessionFactory factory;

    public CustomerDAOImpl() {
        super(Customer.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }
}
