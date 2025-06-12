package ru.buzynnikov.payment_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.payment_service.models.Payment;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("UPDATE Payment p SET p.isPaid = true WHERE p.orderId = :orderId")
    @Modifying
    void makePayment(UUID orderId);
}
