package ru.buzynnikov.order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.order_service.models.Order;


import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {


}
