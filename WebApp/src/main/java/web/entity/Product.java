package web.entity;

import javafx.beans.property.SimpleStringProperty;
import web.enum_types.Capacity;
import web.enum_types.Color;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Entity
public class Product implements Serializable {
    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    private String filename;

    @OneToMany
    @JoinColumn(name = "PRODUCT_ID")
    private List<Picture> pictureList;

    @Enumerated(EnumType.STRING)
    private Capacity capacity;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(length = 1000)
    private String description;

    private transient DecimalFormat format = new DecimalFormat("#0.00");

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

    public Capacity getCapacity() {
        return capacity;
    }

    public String getCapacityString() {
        return capacity.toString();
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public Color getColor() {
        return color;
    }

    public String getColorString() {
        return color.toString();
    }

    public void setColor(Color color) {
        this.color = color;
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

    public String getPriceFormat() {
        return format.format(getPrice());
    }

    public String getPriceVATFormat() {
        return format.format(getPriceVAT());
    }

    public List<Picture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }
}
