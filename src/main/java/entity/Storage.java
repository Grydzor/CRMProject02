package entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by eriol4ik on 24.02.2017.
 */

@Entity
public class Storage implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column
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
}
