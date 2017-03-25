package web.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Picture implements Serializable {

    @Id
    @Column(name = "PICTURE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "IMAGE")
    private String imageLink;

    public Picture() {
    }

    public Picture(Product product, String imageLink) {
        this.product = product;
        this.imageLink = imageLink;
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
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "id=" + id + ", product=" + product.getName() + ", imageLink='" + imageLink;
    }
}
