package dao;

import entity.Employee;
import entity.Item;
import entity.Order;

import java.util.List;

public interface OrderDAO extends DAO<Order> {
    List<Order> findOrdersFor(Employee manager);
    List<Item> findItems(Order order);
}
