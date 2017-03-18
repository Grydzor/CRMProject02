package service;

import entity.Customer;
import entity.Order;

import java.util.List;

public interface CustomerService extends Service<Customer> {
    List<Order> findOrders(Customer customer);

    Customer find(String email);
}
