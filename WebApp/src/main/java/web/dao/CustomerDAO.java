package web.dao;

import web.entity.Customer;
import web.entity.Order;

import java.util.List;

public interface CustomerDAO extends DAO<Customer> {
    List<Order> findOrders(Customer customer);
}
