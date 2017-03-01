package entity;

import enum_types.OrderStatus;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.context.ApplicationContext;
import service.ItemService;
import service.OrderService;
import service.OrderServiceImpl;
import service.StorageService;
import util.ApplicationContextFactory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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

    transient private List<Item> items;

    @Column(name = "STATUS")
    private OrderStatus status;

    @Column(name = "SUMMARY")
    private BigDecimal summary/* = BigDecimal.ZERO*/;

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

    public List<Item> getItems() {
        // id == null <- is it new Order?
        items = items == null ? (id == null ? (new ArrayList<>()) : (ApplicationContextFactory.getApplicationContext().getBean(OrderService.class).findItems(this))) : items;
        updateSummary();
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BigDecimal getSummary() {
        if (summary != null) return summary;

        updateSummary();
        return summary;
    }

    public void setSummary(BigDecimal summary) {
        this.summary = summary;
    }

    // updates summary based on items collection
    // @return summary
    public BigDecimal updateSummary() {
        BigDecimal summary = BigDecimal.ZERO;
        if (items == null) items = getItems();
        for (Item item : items) {
            summary = summary.add(item.getSumVAT());
        }
        if (!summary.equals(this.summary)) {
            this.summary = summary;
            if (this.id != null) {
                ApplicationContextFactory.getApplicationContext()
                        .getBean("orderService", OrderService.class)
                        .update(this);
            }
        }
        return summary;
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
