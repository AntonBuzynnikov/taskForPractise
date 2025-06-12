package ru.buzynnikov.shipping_service.services.interfaces;

import java.util.UUID;

public interface ShippingService {
    void shipping(UUID orderId);
}
