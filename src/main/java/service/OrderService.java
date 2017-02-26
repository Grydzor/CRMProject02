package service;

import entity.Employee;
import entity.Item;
import entity.Order;

import java.util.List;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public interface OrderService extends Service<Order> {
    List<Order> findAllFor(Employee manager);
    List<Item> findItems(Order order);
}
