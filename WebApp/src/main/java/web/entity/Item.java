package web.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Table(name = "items")
@Entity
public class Item implements Serializable {
    @Id
    @Column(name = "ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    private transient SimpleIntegerProperty availability;
    private transient SimpleStringProperty inStock;

    private transient DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public Item() {}

    public Item(Product product, Integer amount, Order order) {
        setProduct(product);
        this.amount = amount;
        setOrder(order);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
        setPrice(product.getPrice());
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        if (order != null && price != null && amount != null) {
            order.setSummary(order.getSummary()
                                .add((price.subtract(this.price))
                                .multiply(BigDecimal.valueOf(amount))
                                .multiply(BigDecimal.valueOf(1.2))));
        }
        if (price != null) {
            this.price = price;
        }
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        if (order != null && price != null && amount != null) {
            order.setSummary(order.getSummary()
                                .add(price.multiply(BigDecimal.valueOf(amount - this.amount))
                                .multiply(BigDecimal.valueOf(1.2))));
            order.setAmount(order.getAmount() + (amount - this.amount));
            if (amount == 0) {
                order.getItems().remove(this);
            }
        }
        if (amount != null) {
            this.amount = amount;
        }
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
        if (order != null && price != null && amount != null) {
            order.setSummary(order.getSummary()
                    .add(price.multiply(BigDecimal.valueOf(amount))
                              .multiply(BigDecimal.valueOf(1.2))));
            order.setAmount(order.getAmount() + amount);
        }
    }

    public BigDecimal getPriceVAT() {
        return price.multiply(BigDecimal.valueOf(1.2));
    }
    public BigDecimal getSumNoVAT() {
        return price.multiply(BigDecimal.valueOf(amount));
    }
    public BigDecimal getSumVAT() {
        return getPriceVAT().multiply(BigDecimal.valueOf(amount));
    }

    // for jsp displaying
    public String getPriceVATFormat() {
        return decimalFormat.format(getPriceVAT());
    }
    public String getSumVATFormat() {
        return decimalFormat.format(getSumVAT());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", product=" + product +
                ", price=" + price +
                ", amount=" + amount +
                ", order=" + order +
                '}';
    }
}