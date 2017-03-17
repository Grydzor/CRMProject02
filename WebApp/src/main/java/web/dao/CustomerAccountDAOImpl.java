package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.entity.Customer;
import web.entity.CustomerAccount;

@Repository("customerAccountDAO")
public class CustomerAccountDAOImpl extends DAOImpl<CustomerAccount> implements CustomerAccountDAO {
    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private SessionFactory factory;

    @Autowired
    protected CustomerAccountDAOImpl() {}

    @Override
    public String findPass(String email) {
        Customer customer = customerDAO.find(email);
        return this.read(customer.getId()).getPassword();
    }
}
