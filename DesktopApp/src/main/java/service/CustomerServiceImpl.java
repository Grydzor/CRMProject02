package service;

import dao.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import entity.Customer;
import entity.Order;
import service.CustomerService;

import java.util.List;

@Service("customerService")
public class CustomerServiceImpl extends ServiceImpl<Customer> implements CustomerService {
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
}
