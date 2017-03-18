package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ACCOUNTS")
public class CustomerAccount {
    @Id
    private Long customerId;

    @Column
    private String password;

    public CustomerAccount() {}

    public CustomerAccount(Long customerId, String password) {
        this.customerId = customerId;
        this.password = password;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
