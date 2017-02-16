package service;

import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import entity.Customer;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class CustomerServiceImpl extends ServiceImpl<Customer> implements CustomerService {
    CustomerDAO customerDAO;

    public CustomerServiceImpl() {
        super(Customer.class);
        customerDAO = new CustomerDAOImpl();
    }
}
