package web.dao;

import web.entity.Employee;
import web.entity.Item;
import web.entity.Order;

import java.util.List;

public interface OrderDAO extends DAO<Order, Long> {
    List<Order> findOrdersFor(Employee manager);
    List<Item> findItems(Order order);
}
