package ru.buzynnikov.payment_service.services.interfaces;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {
    void makePayment(UUID orderId, BigDecimal amount);
}
