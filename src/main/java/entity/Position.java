package entity;

import javax.persistence.*;

/**
 * Created by Никита on 15.02.2017.
 */

@Entity
@Table(name = "POSITIONS")
public class Position {

    @Id
    @Column(name = "POSITION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "AMOUNT")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    public Position() {
    }

    public Position(Product product, Integer price, Integer amount, Order order) {
        this.product = product;
        this.price = price;
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

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", product=" + product +
                ", price=" + price +
                ", amount=" + amount +
                ", order=" + order +
                '}';
    }
}
