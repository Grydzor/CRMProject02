package dao;

import entity.Customer;
import entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateSessionFactory;

import java.util.List;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class CustomerDAOImpl extends DAOImpl<Customer> implements CustomerDAO {
    private static SessionFactory factory;
    private static CustomerDAOImpl singleton;

    private CustomerDAOImpl() {
        super(Customer.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }

    public static CustomerDAOImpl getInstance() {
        if (singleton == null) singleton = new CustomerDAOImpl();
        return singleton;
    }

    @Override
    public List<Order> findOrders(Customer customer) {
        try (Session session = factory.openSession()) {
            Query<Order> query = session.createQuery("Select o FROM Order o WHERE o.customer = :customer", Order.class);
            query.setParameter("customer", customer);
            return query.getResultList();
        }
    }
}
