package dao;

import entity.CustomerAccount;

public interface CustomerAccountDAO extends DAO<CustomerAccount> {
    String findPass(String email);
}
