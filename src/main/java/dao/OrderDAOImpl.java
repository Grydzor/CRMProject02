package dao;

import entity.Order;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class OrderDAOImpl extends DAOImpl<Order> implements OrderDAO {
    private SessionFactory factory;

    public OrderDAOImpl() {
        super(Order.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }
}
