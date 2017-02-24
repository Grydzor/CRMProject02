package dao;

import entity.Customer;
import entity.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("customerDAO")
public class CustomerDAOImpl extends DAOImpl<Customer> implements CustomerDAO {

    @Autowired
    protected CustomerDAOImpl() {
        super(Customer.class);
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
