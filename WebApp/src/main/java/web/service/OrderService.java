package web.service;

import web.entity.Employee;
import web.entity.Item;
import web.entity.Order;

import java.util.List;

public interface OrderService extends Service<Order, Long> {
    List<Order> findAllFor(Employee manager);
    Order readWithItems(Long id);
}
