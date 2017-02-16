package dao;

import entity.Customer;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class CustomerDAOImpl extends DAOImpl<Customer> implements CustomerDAO {
    public CustomerDAOImpl() {
        super(Customer.class);
    }
}
