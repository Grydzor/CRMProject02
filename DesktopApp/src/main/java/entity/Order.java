package entity;

import enum_types.OrderStatus;
import javafx.beans.property.SimpleStringProperty;
import service.OrderService;
import util.ApplicationContextFactory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @OneToMany
    @JoinColumn(name = "PAYMENT_ID")
    private List<Payment> paymentList;

    @OneToMany
    @JoinColumn(name = "DELIVERY_ID")
    private List<Delivery> deliveryList;

//    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", nullable = false)
    private Date date;

//    @Temporal(TemporalType.DATE)
    @Column(name = "DEADLINE")
    private Date deadline;

    /*@OneToMany(mappedBy = "order")
    private List<Item> items = new ArrayList<>();*/

    transient private List<Item> items;

    @Column(name = "STATUS")
    private OrderStatus status;

    @Column(name = "SUMMARY")
    private BigDecimal summary;

    @Column
    private Integer amount;

    private transient DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public Order() {
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

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }

    public List<Item> getItems() {
        // id == null <- is it new Order?
        items = items == null ? (id == null ? (new ArrayList<>()) : (ApplicationContextFactory.getApplicationContext().getBean(OrderService.class).findItems(this))) : items;
        updateSummaryAndAmount();
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BigDecimal getSummary() {
        if (summary != null) return summary;

        updateSummaryAndAmount();
        return summary;
    }

    public void setSummary(BigDecimal summary) {
        this.summary = summary;
    }

    // updates summary based on items collection
    // @return summary
    public void updateSummaryAndAmount() {
        BigDecimal summary = BigDecimal.ZERO;
        Integer amount = 0;
        if (items == null) items = getItems();
        for (Item item : items) {
            summary = summary.add(item.getSumVAT());
            amount = amount + item.getAmount();
        }
        if (!amount.equals(this.amount)) {
            this.amount = amount;
            if (this.id != null) {
                ApplicationContextFactory.getApplicationContext()
                        .getBean("orderService", OrderService.class)
                        .update(this);
            }
        }
        summary = summary.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (!summary.equals(this.summary)) {
            this.summary = summary;
            if (this.id != null) {
                ApplicationContextFactory.getApplicationContext()
                        .getBean("orderService", OrderService.class)
                        .update(this);
            }
        }
    }

    public Integer getAmount() {
        if (amount != null) return amount;

        updateSummaryAndAmount();
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public SimpleStringProperty summaryProperty() {
        return new SimpleStringProperty(decimalFormat.format(getSummary()));
    }

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
