package web.dao;

import web.entity.Customer;
import web.entity.CustomerAccount;

public interface CustomerAccountDAO extends DAO<CustomerAccount, String> {
    String findPass(String email);
}
