package entity;

import com.sun.istack.internal.NotNull;
import service.CustomerServiceImpl;
import service.OrderServiceImpl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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

    /*@OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();*/

    transient private List<Order> orders = new ArrayList<>();

    public Customer() {}

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
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

    public List<Order> getOrders() {
//        return orders;
        return orders.isEmpty() ? (orders = CustomerServiceImpl.getInstance().findOrders(this)) : orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
