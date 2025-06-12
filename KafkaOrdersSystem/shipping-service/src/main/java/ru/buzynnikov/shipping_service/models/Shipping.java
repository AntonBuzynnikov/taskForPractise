package ru.buzynnikov.shipping_service.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "shipping")
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID orderId;

    public Shipping() {}

    public Shipping(UUID orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipping shipping = (Shipping) o;
        return Objects.equals(id, shipping.id) && Objects.equals(orderId, shipping.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Shipping{");
        sb.append("id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append('}');
        return sb.toString();
    }
}
