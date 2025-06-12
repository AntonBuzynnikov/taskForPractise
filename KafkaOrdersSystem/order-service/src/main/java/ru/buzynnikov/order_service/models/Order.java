package ru.buzynnikov.order_service.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private UUID orderId;

    @ManyToMany
    @JoinTable(name = "order_product",
    joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    private BigDecimal totalPrice;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Status status;


    public Order() {

    }

    public Order(String orderId, List<Product> products, BigDecimal totalPrice, Long userId, String status) {
        this.orderId = UUID.fromString(orderId);
        this.products = products;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.status = Status.valueOf(status);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(products, order.products) && Objects.equals(totalPrice, order.totalPrice) && Objects.equals(userId, order.userId) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, products, totalPrice, userId, status);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", products=").append(products);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", userId=").append(userId);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
