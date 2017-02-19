package entity;

import enum_types.OrderStatus;
import service.OrderServiceImpl;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Created by Никита on 15.02.2017.
 */

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "MANAGER_ID", nullable = false)
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

//    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private Date date;

//    @Temporal(TemporalType.DATE)
    @Column(name = "DEADLINE", nullable = false)
    private Date deadline;

    /*@OneToMany(mappedBy = "order")
    private List<Item> items = new ArrayList<>();*/

    transient private List<Item> items = new ArrayList<>();

    @Column(name = "STATUS")
    private OrderStatus status;

/*
    @Column(name = "SUMMARY")
    private BigDecimal summary;
*/


    public Order() {
    }

    public Order(Employee manager, Customer customer, Date deadline, OrderStatus status/*, BigDecimal summary*/) {
        this.manager = manager;
        this.customer = customer;
        this.deadline = deadline;
        this.status = status;
        this.date = Date.valueOf(LocalDate.now());
        /*this.summary = summary;*/
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Item> getItems() {
//        return items;
        return items.isEmpty() ? (items = OrderServiceImpl.getInstance().findItems(this)) : items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

/*    public BigDecimal getSummary() {return summary;}

    public void setSummary(BigDecimal summary) {this.summary = summary;}*/

    @Override
    public String toString() {
        return "Order{" +
                "manager='" + manager.shortInfo() + "'" +
                ", customer='" + customer + "'" +
                ", date='" + date + "'" +
                ", status='" + status + "'" +
                /*", summary='" + summary + "'" +*/
                '}';
    }
}
