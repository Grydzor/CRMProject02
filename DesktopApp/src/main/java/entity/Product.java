package entity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {

    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column
    private String filename;

    @Column(length = 511)
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }
}
