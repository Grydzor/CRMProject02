package entity;

import enum_types.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

    @Column(name = "MANAGER")
    private String manager;

    @Column(name = "BUYER")
    private String buyer;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    @Column(name = "STATUS")
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private Collection<Item> items = new ArrayList<>();

    @Column(name = "SUMMARY")
    private BigDecimal summary;

    public Order() {
    }

    public Order(String manager, String buyer, Date date, OrderStatus status, BigDecimal summary) {
        this.manager = manager;
        this.buyer = buyer;
        this.date = date;
        this.status = status;
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public String getSummary() {
        return summary.toString();
    }

    public void setSummary(String summary) {
        this.summary = new BigDecimal(summary);
    }

}
