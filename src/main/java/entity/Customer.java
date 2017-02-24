package entity;

import service.CustomerService;
import util.ApplicationContextFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eriol4ik on 16.02.2017.
 */

@Entity
@Table(name = "CUSTOMERS")
public class Customer {

    @Id
    @Column(name = "CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "EMAIL")
    private String email;

    /*@OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();*/

    transient private List<Order> orders = new ArrayList<>();

    public Customer() {}

    public Customer(String name, String surname, String mobile, String email) {
        this.name = name;
        this.surname = surname;
        this.mobile = mobile;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders != null && orders.isEmpty() ? orders = ApplicationContextFactory
                .getApplicationContext()
                .getBean(CustomerService.class)
                .findOrders(this) : orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
