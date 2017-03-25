package web.entity;

import web.enum_types.DeliveryType;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Delivery {
    @Id
    @Column(name = "DELIVERY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Column
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(length = 1000)
    private String description;

    public Delivery() {
    }

    public Delivery(String address, DeliveryType deliveryType, Date date, Order order) {
        this.address = address;
        this.deliveryType = deliveryType;
        this.date = date;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
