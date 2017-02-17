package service;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import entity.Order;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class OrderServiceImpl extends ServiceImpl<Order> implements OrderService {
    private OrderDAO orderDAO;
    private static OrderServiceImpl singleton;

    private OrderServiceImpl() {
        super(Order.class);
        orderDAO = OrderDAOImpl.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        if (singleton == null) singleton = new OrderServiceImpl();
        return singleton;
    }
}
