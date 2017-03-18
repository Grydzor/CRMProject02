package service;

import dao.CustomerAccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import entity.CustomerAccount;

@Service("customerAccountService")
public class CustomerAccountServiceImpl extends ServiceImpl<CustomerAccount> implements CustomerAccountService {
    @Autowired
    @Qualifier("customerAccountDAO")
    private CustomerAccountDAO customerAccountDAO;

    private CustomerAccountServiceImpl() {}

    @Override
    @Transactional
    public String findPass(String email) {
        return customerAccountDAO.findPass(email);
    }
}
