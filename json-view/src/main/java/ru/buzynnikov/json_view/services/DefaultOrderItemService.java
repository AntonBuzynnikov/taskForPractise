package ru.buzynnikov.json_view.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.json_view.exceptions.OrderItemNotFoundException;
import ru.buzynnikov.json_view.models.OrderItem;
import ru.buzynnikov.json_view.repositoies.OrderItemRepository;
import ru.buzynnikov.json_view.services.interfaces.OrderItemService;

@Service
public class DefaultOrderItemService implements OrderItemService {

    private final OrderItemRepository repository;

    public DefaultOrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public void update(Long orderId, Long productId, Integer itemQuantity) {
        OrderItem orderItem = repository.findByOrderIdAndProductId(orderId, productId).orElseThrow(() -> new OrderItemNotFoundException("Order item not found"));
        orderItem.setQuantity(itemQuantity);
        repository.save(orderItem);
    }
}
