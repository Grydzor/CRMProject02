package entity;

import enum_types.DeliveryType;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class Delivery {
    @Id
    @Column(name = "DELIVERY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ADRESS")
    private String adress;

    @Column
    @Enumerated
    private DeliveryType deliveryType;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "Description")
    private String description;

    public Delivery() {
    }

    public Delivery(String adress, DeliveryType deliveryType, Date date, Order order) {
        this.adress = adress;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
