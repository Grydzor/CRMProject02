package service;

import entity.CustomerAccount;

public interface CustomerAccountService extends Service<CustomerAccount> {
    String findPass(String email);
}
