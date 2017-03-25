package web.dao;

import web.entity.Customer;
import web.entity.Order;

import java.util.List;

public interface CustomerDAO extends DAO<Customer, Long> {
    List<Order> findOrders(Customer customer);

    Customer find(String email);
}
