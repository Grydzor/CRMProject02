package service;

import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import entity.Customer;
import entity.Order;

import java.util.List;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class CustomerServiceImpl extends ServiceImpl<Customer> implements CustomerService {
    private CustomerDAO customerDAO;
    private static CustomerServiceImpl singleton;

    private CustomerServiceImpl() {
        super(Customer.class);
        customerDAO = CustomerDAOImpl.getInstance();
    }

    public static CustomerServiceImpl getInstance() {
        if (singleton == null) singleton = new CustomerServiceImpl();
        return singleton;
    }

    @Override
    public List<Order> findOrders(Customer customer) {
        return customerDAO.findOrders(customer);
    }
}
