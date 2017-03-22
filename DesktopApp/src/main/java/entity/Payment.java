package entity;

import enum_types.PaymentType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Entity
public class Payment {
    @Id
    @Column(name = "PAYMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated
    private PaymentType paymentType;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "Description")
    private String description;

    public Payment() {
    }

    public Payment(PaymentType paymentType, BigDecimal amount, Order order) {
        this.paymentType = paymentType;
        this.amount = amount;
        this.date = Date.valueOf(LocalDate.now());
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
