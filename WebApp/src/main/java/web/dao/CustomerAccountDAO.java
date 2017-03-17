package web.dao;

import web.entity.CustomerAccount;

public interface CustomerAccountDAO extends DAO<CustomerAccount> {
    String findPass(String email);
}
