package ru.buzynnikov.json_view.repositoies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.buzynnikov.json_view.models.OrderItem;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi FROM OrderItem oi WHERE oi.userOrder.id = :orderId AND oi.product.id = :productId")
    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);
}