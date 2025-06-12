package ru.buzynnikov.order_service.services;


import org.springframework.stereotype.Service;
import ru.buzynnikov.order_service.aspects.ToLog;
import ru.buzynnikov.order_service.dto.CreateOrderRequest;
import ru.buzynnikov.order_service.dto.OrderResponse;
import ru.buzynnikov.order_service.exceptions.OrderNotFoundException;
import ru.buzynnikov.order_service.messages.out.KafkaSender;
import ru.buzynnikov.order_service.models.Order;
import ru.buzynnikov.order_service.models.Product;
import ru.buzynnikov.order_service.models.Status;
import ru.buzynnikov.order_service.repositories.OrderRepository;
import ru.buzynnikov.order_service.services.interfaces.OrderService;
import ru.buzynnikov.order_service.services.interfaces.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final String TOPIC_NAME = "new_order";

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final KafkaSender kafkaSender;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, KafkaSender kafkaSender) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.kafkaSender = kafkaSender;
    }


    @ToLog
    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = mapToOrder(request);
        sendNewOrderToKafka(order);
        return mapToOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse getOrder(UUID orderId) {
        return mapToOrderResponse(orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Заказ с id " + orderId + " не найден")));
    }

    @ToLog
    @Override
    public void updateStatus(Status status, UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Заказ с id " + orderId + " не найден"));
        order.setStatus(status);
        orderRepository.save(order);
    }


    private List<Product> getProductsByIds(List<Long> ids){
        return productService.findAllById(ids);
    }

    private BigDecimal getTotalPrice(List<Product> products){
        return products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    private OrderResponse mapToOrderResponse(Order order){
        return new OrderResponse(order.getOrderId(), order.getTotalPrice(), order.getStatus());
    }

    private Order mapToOrder(CreateOrderRequest request){
        Order order = new Order();
        List<Product> products = getProductsByIds(request.productsId());
        order.setOrderId(UUID.randomUUID());
        order.setProducts(products);
        order.setTotalPrice(getTotalPrice(products));
        order.setStatus(Status.CREATED);
        order.setUserId(request.userId());
        return order;
    }

    private void sendNewOrderToKafka(Order order){
        kafkaSender.sendNewOrder(order, TOPIC_NAME);
    }

}
