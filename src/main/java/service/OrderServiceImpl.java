package service;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import entity.Order;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class OrderServiceImpl extends ServiceImpl<Order> implements OrderService {
    OrderDAO orderDAO;

    public OrderServiceImpl() {
        super(Order.class);
        orderDAO = new OrderDAOImpl();
    }
}
