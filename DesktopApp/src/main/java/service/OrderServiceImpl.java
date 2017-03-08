package service;

import dao.OrderDAO;
import entity.Employee;
import entity.Item;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<Order> implements OrderService {
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
    public List<Item> findItems(Order order) {
        return orderDAO.findItems(order);
    }
}
