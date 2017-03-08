package service;

import entity.Employee;
import entity.Item;
import entity.Order;

import java.util.List;

public interface OrderService extends Service<Order> {
    List<Order> findAllFor(Employee manager);
    List<Item> findItems(Order order);
}
