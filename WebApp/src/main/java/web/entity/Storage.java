package web.entity;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
public class Storage implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private Integer amount;

    public Storage() {}

    public Storage(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getName() {
        return product.getName();
    }

    public BigDecimal getPrice() {
        return product.getPrice();
    }

    @Override
    public String toString() {
        return "Storage{" +
                "product=" + product +
                ", amount=" + amount +
                '}';
    }
}
