package service;

import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import entity.Customer;
import entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customerService")
public class CustomerServiceImpl extends CustomerDAOImpl implements CustomerService {
    @Autowired
    @Qualifier("customerDAO")
    private CustomerDAO customerDAO;

    private CustomerServiceImpl() {
        super();
    }


    @Override
    public List<Order> findOrders(Customer customer) {
        return customerDAO.findOrders(customer);
    }

}
