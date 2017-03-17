package web.service;

import web.entity.CustomerAccount;

public interface CustomerAccountService extends Service<CustomerAccount> {
    String findPass(String email);
}
