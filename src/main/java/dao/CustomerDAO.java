package dao;

import entity.Customer;
import entity.Order;

import java.util.List;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public interface CustomerDAO extends DAO<Customer> {
    List<Order> findOrders(Customer customer);
}
