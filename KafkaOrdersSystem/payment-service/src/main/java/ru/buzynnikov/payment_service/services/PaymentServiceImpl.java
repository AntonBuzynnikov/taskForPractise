package ru.buzynnikov.payment_service.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.payment_service.aspects.ToLog;
import ru.buzynnikov.payment_service.messages.out.KafkaSender;
import ru.buzynnikov.payment_service.models.Payment;
import ru.buzynnikov.payment_service.repositories.PaymentRepository;
import ru.buzynnikov.payment_service.services.interfaces.PaymentService;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final KafkaSender kafkaSender;

    public PaymentServiceImpl(PaymentRepository paymentRepository, KafkaSender kafkaSender) {
        this.paymentRepository = paymentRepository;
        this.kafkaSender = kafkaSender;
    }

    @ToLog
    @Override
    public void makePayment(UUID orderId, BigDecimal amount) {
        paymentRepository.save(createPayment(orderId, amount));
        kafkaSender.sendNewOrder(orderId,"payed_order");
    }


    private Payment createPayment(UUID orderId, BigDecimal amount) {
        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID());
        payment.setOrderId(orderId);
        payment.setPaid(false);
        payment.setAmount(amount);
        return payment;
    }


}
