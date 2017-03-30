package web.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import web.enum_types.OrderStatus;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
public class Order implements Serializable {
    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "ORDER_ID")
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "ORDER_ID")
    private Delivery delivery;

    private Date date;

    private Date deadline;

    @OneToMany(orphanRemoval = true)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @JoinColumn(name = "ORDER_ID")
    private List<Item> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal summary = BigDecimal.ZERO;

    private Integer amount = 0;

    @Column(length = 1000)
    private String description;

    private transient DecimalFormat format = new DecimalFormat("#0.00");

    public Order() {
        System.out.println("creating order");
    }

    public Order(Employee manager, Customer customer, Date deadline) {
        this.manager = manager;
        this.customer = customer;
        this.deadline = deadline;
        this.status = OrderStatus.OPENED;
        this.date = Date.valueOf(LocalDate.now());
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BigDecimal getSummary() {
        return summary;
    }

    public String getUpdatedSummary() {
        return format.format(summary);
    }

    public String getSummaryFormat() {
        return format.format(getSummary());
    }

    public void setSummary(BigDecimal summary) {
        this.summary = summary;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("destroyed!");
    }

    /*@Override
    public String toString() {
        return "Order{" +
                "manager='" + manager.shortInfo() + "'" +
                ", customer='" + customer + "'" +
                ", date='" + date + "'" +
                ", status='" + status + "'" +
                ", summary='" + summary + "'" +
                '}';
    }*/
}
