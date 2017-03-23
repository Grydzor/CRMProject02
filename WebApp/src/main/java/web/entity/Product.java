package web.entity;


import javafx.beans.property.SimpleStringProperty;
import web.enum_types.IPhoneCapacity;
import web.enum_types.IPhoneColor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {
    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String filename;

    @Column
    @Enumerated(EnumType.STRING)
    private IPhoneCapacity iPhoneCapacity;

    @Column
    @Enumerated(EnumType.STRING)
    private IPhoneColor iPhoneColor;

    @Column(length = 1000)
    private String description;

    private transient DecimalFormat decimalFormat = new DecimalFormat("#0.00");


    public Product() {}

    public Product(String name, BigDecimal price, String pathImage, String description) {
        this.name = name;
        this.price = price;
        this.filename = pathImage;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {return price;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public IPhoneCapacity getiPhoneCapacity() {
        return iPhoneCapacity;
    }

    public void setiPhoneCapacity(IPhoneCapacity iPhoneCapacity) {
        this.iPhoneCapacity = iPhoneCapacity;
    }

    public IPhoneColor getiPhoneColor() {
        return iPhoneColor;
    }

    public void setiPhoneColor(IPhoneColor iPhoneColor) {
        this.iPhoneColor = iPhoneColor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPriceVAT() {
        return price.multiply(BigDecimal.valueOf(1.2));
    }

    public String getPriceVATFormat() {
        return priceVATProperty().get();
    }

    public SimpleStringProperty priceVATProperty() {
        return new SimpleStringProperty(decimalFormat.format(getPriceVAT()));
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }
}
