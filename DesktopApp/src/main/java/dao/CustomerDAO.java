package dao;

import entity.Customer;
import entity.Order;

import java.util.List;

public interface CustomerDAO extends DAO<Customer> {
    List<Order> findOrders(Customer customer);
}
