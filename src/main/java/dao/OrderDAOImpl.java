package dao;

import entity.Employee;
import entity.Item;
import entity.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("orderDAO")
public class OrderDAOImpl extends DAOImpl<Order> implements OrderDAO {

    @Autowired
    protected OrderDAOImpl() {
        super(Order.class);
    }


    @Override
    public List<Order> findOrdersFor(Employee manager) {
        try (Session session = factory.openSession()) {
            Query<Order> query = session.createQuery("FROM Order o WHERE o.manager = :manager", Order.class);
            query.setParameter("manager", manager);
            return query.getResultList();
        }
    }

    @Override
    public List<Item> findItems(Order order) {
        try (Session session = factory.openSession()) {
            Query<Item> query = session.createQuery("SELECT i FROM Item i WHERE i.order = :order", Item.class);
            query.setParameter("order", order);
            return query.getResultList();
        }
    }
}
