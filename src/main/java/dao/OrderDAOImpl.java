package dao;

import entity.Item;
import entity.Order;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.HibernateProxyHelper;
import org.hibernate.query.Query;
import util.HibernateSessionFactory;

import java.util.List;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class OrderDAOImpl extends DAOImpl<Order> implements OrderDAO {
    private SessionFactory factory;
    private static OrderDAOImpl singleton;

    private OrderDAOImpl() {
        super(Order.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }

    public static OrderDAOImpl getInstance() {
        if (singleton == null) singleton = new OrderDAOImpl();
        return singleton;
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
