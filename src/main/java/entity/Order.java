package entity;

import enum_types.OrderStatus;

import javax.persistence.*;
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
    private Collection<Position> positions = new ArrayList<>();

    @Column(name = "SUMMARY")
    private Integer summary;

    public Order() {
    }

    public Order(String manager, String buyer, Date date, OrderStatus status, Integer summary) {
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

    public Collection<Position> getPositions() {
        return positions;
    }

    public void setPositions(Collection<Position> positions) {
        this.positions = positions;
    }

    public Integer getSummary() {
        return summary;
    }

    public void setSummary(Integer summary) {
        this.summary = summary;
    }

}
