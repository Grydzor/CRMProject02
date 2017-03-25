package web.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CustomerAccount implements Serializable {
    @Id
    private String email;

    // todo: hash
    private String password;

    public CustomerAccount() {}

    public CustomerAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
