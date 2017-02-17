package dao;

import entity.Order;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

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
}
