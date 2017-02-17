package service;

import entity.Customer;
import entity.Order;

import java.util.List;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public interface CustomerService extends Service<Customer> {
    List<Order> findOrders(Customer customer);
}
