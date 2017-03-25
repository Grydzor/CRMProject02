package web.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
public class Item {
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
        this.product = product;
        this.price = product.getPrice();
        this.amount = amount;
        this.order = order;
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
    public void setProduct(Product product) {this.product = product;}
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
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