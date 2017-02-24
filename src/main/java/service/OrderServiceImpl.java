package service;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import entity.Item;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl extends OrderDAOImpl implements OrderService {
    @Autowired
    @Qualifier("orderDAO")
    private OrderDAO orderDAO;

    private OrderServiceImpl() {
        super();
    }

    @Override
    public List<Item> findItems(Order order) {
        return orderDAO.findItems(order);
    }
}
