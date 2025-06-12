package ru.buzynnikov.shipping_service.services.impl;


import org.springframework.stereotype.Service;
import ru.buzynnikov.shipping_service.aspects.ToLog;
import ru.buzynnikov.shipping_service.messages.in.KafkaSender;
import ru.buzynnikov.shipping_service.models.Shipping;
import ru.buzynnikov.shipping_service.repositories.ShippingRepository;
import ru.buzynnikov.shipping_service.services.interfaces.ShippingService;

import java.util.UUID;


@Service
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

    private final KafkaSender kafkaSender;

    public ShippingServiceImpl(ShippingRepository shippingRepository, KafkaSender kafkaSender) {
        this.shippingRepository = shippingRepository;
        this.kafkaSender = kafkaSender;
    }

    @ToLog
    @Override
    public void shipping(UUID orderId) {
        shippingRepository.save(createShipping(orderId));
        sendMessage(orderId);
    }

    private Shipping createShipping(UUID orderId){
        return new Shipping(orderId);
    }

    private void sendMessage(UUID orderId) {
        kafkaSender.sendMessage("sent_order", orderId);
    }
}
