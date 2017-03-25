package web.service;

import web.entity.Customer;
import web.entity.Order;

import java.util.List;

public interface CustomerService extends Service<Customer, Long> {
    List<Order> findOrders(Customer customer);

    Customer find(String email);

    Customer readWithAccount(Long id);

    Customer readWithOrders(Long id);
}
