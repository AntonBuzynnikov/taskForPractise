package ru.buzynnikov.json_view.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.json_view.dto.CreateOrderDto;
import ru.buzynnikov.json_view.dto.CreateOrderItemDto;
import ru.buzynnikov.json_view.exceptions.OrderNotFoundException;
import ru.buzynnikov.json_view.models.UserOrder;
import ru.buzynnikov.json_view.models.OrderItem;
import ru.buzynnikov.json_view.models.Status;
import ru.buzynnikov.json_view.models.User;
import ru.buzynnikov.json_view.repositoies.OrderRepository;
import ru.buzynnikov.json_view.services.interfaces.OrderService;
import ru.buzynnikov.json_view.services.interfaces.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    @PersistenceContext
    private EntityManager entityManager;

    public DefaultOrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    public UserOrder create(CreateOrderDto dto, Long userId) {
        User userProxy = entityManager.getReference(User.class, userId);
        UserOrder userOrder = new UserOrder();
        Set<OrderItem> orderItems = mapOrderItems(dto.items(), userOrder);
        System.out.println(orderItems);
        userOrder.setItems(orderItems);
        userOrder.setUser(userProxy);
        userOrder.setStatus(Status.OPEN);
        userOrder.setTotalPrice(getTotalPrice(orderItems));
        return orderRepository.save(userOrder);
    }

    @Override
    public void update(CreateOrderDto dto, Long orderId) {
        UserOrder userOrder = getById(orderId);
        Set<OrderItem> orderItems = mapOrderItems(dto.items(), userOrder);
        userOrder.setItems(orderItems);
        userOrder.setTotalPrice(getTotalPrice(orderItems));
        orderRepository.save(userOrder);
    }

    @Override
    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public UserOrder getById(Long orderId) {
        return orderRepository.findOrderWithAllDataById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    private Set<OrderItem> mapOrderItems(List<CreateOrderItemDto> orderItems, UserOrder userOrder) {
        return orderItems.stream().map(orderItemDto ->{
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(productService.getProductById(orderItemDto.productId()));
            orderItem.setOrder(userOrder);
            orderItem.setQuantity(orderItemDto.quantity());
            return orderItem;
        }).collect(Collectors.toSet());
    }

    private BigDecimal getTotalPrice(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
