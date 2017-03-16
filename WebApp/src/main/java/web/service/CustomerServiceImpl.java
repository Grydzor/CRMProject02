package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import web.dao.CustomerDAO;
import web.entity.Customer;
import web.entity.Order;

import java.util.List;

@Service("customerService")
public class CustomerServiceImpl extends ServiceImpl<Customer> implements CustomerService {
    @Autowired
    @Qualifier("customerDAO")
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerServiceImpl() {}

    @Override
    public List<Order> findOrders(Customer customer) {
        return customerDAO.findOrders(customer);
    }
}
