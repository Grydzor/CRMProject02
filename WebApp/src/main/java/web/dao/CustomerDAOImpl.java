package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.entity.Customer;
import web.entity.Order;

import java.util.List;

@Repository("customerDAO")
public class CustomerDAOImpl extends DAOImpl<Customer> implements CustomerDAO {
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected CustomerDAOImpl() {}

    @Override
    public List<Order> findOrders(Customer customer) {
        return factory.getCurrentSession()
                .createQuery("Select o FROM Order o WHERE o.customer = :customer", Order.class)
                .setParameter("customer", customer)
                .list();
    }

    @Override
    public Customer find(String email) {
        List<Customer> result = factory.getCurrentSession()
                .createQuery("FROM Customer c WHERE c.email = :email", Customer.class)
                .setParameter("email", email)
                .list();
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }
}