package web.entity;

import web.enum_types.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
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

    @OneToMany
    @JoinColumn(name = "ORDER_ID")
    private List<Payment> paymentList;

    @OneToMany
    @JoinColumn(name = "ORDER_ID")
    private List<Delivery> deliveryList;

    @Column(nullable = false)
    private Date date;

    private Date deadline;

    @OneToMany
    @JoinColumn(name = "ORDER_ID")
    private List<Item> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal summary;

    private Integer amount;

    @Column(length = 1000)
    private String description;

    private transient DecimalFormat format = new DecimalFormat("#0.00");

    public Order() {
        this.date = Date.valueOf(LocalDate.now());
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
        if (summary != null) return summary;

        updateSummaryAndAmount();
        return summary;
    }

    public String getSummaryFormat() {
        return format.format(getSummary());
    }

    public void setSummary(BigDecimal summary) {
        this.summary = summary;
    }

    // updates summary based on items collection
    // @return summary
    private void updateSummaryAndAmount() {
        BigDecimal summary = BigDecimal.ZERO;
        Integer amount = 0;
        if (items == null) items = getItems();
        for (Item item : items) {
            summary = summary.add(item.getSumVAT());
            amount = amount + item.getAmount();
        }
        if (!amount.equals(this.amount)) {
            this.amount = amount;
        }
        summary = summary.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (!summary.equals(this.summary)) {
            this.summary = summary;
        }
    }

    public Integer getAmount() {
        updateSummaryAndAmount();
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
