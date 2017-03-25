package web.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.OrderDAO;
import web.entity.Employee;
import web.entity.Item;
import web.entity.Order;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<Order, Long> implements OrderService {
    @Autowired
    @Qualifier("orderDAO")
    private OrderDAO orderDAO;

    private OrderServiceImpl() {}

    @Override
    @Transactional
    public List<Order> findAllFor(Employee manager) {
        return orderDAO.findOrdersFor(manager);
    }

    @Override
    @Transactional
    public Order readWithItems(Long id) {
        Order order = orderDAO.read(id);
        if (order != null) {
            Hibernate.initialize(order.getItems());
        }
        return order;
    }
}
