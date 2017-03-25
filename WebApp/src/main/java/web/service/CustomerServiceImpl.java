package web.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.CustomerDAO;
import web.entity.Customer;
import web.entity.Order;

import java.util.List;

@Service("customerService")
public class CustomerServiceImpl extends ServiceImpl<Customer, Long> implements CustomerService {
    @Autowired
    @Qualifier("customerDAO")
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerServiceImpl() {}

    @Override
    @Transactional
    public List<Order> findOrders(Customer customer) {
        return customerDAO.findOrders(customer);
    }

    @Override
    @Transactional
    public Customer find(String email) {
        return customerDAO.find(email);
    }

    @Override
    @Transactional
    public Customer readWithAccount(Long id) {
        Customer customer = customerDAO.read(id);
        if (customer != null) {
            Hibernate.initialize(customer.getAccount());
        }
        return customer;
    }

    @Override
    public Customer readWithOrders(Long id) {
        Customer customer = customerDAO.read(id);
        if (customer != null) {
            Hibernate.initialize(customer.getOrders());
        }
        return customer;
    }

}
