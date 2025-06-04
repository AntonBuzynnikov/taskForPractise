package ru.buzynnikov.json_view.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.buzynnikov.json_view.dto.CreateOrderDto;
import ru.buzynnikov.json_view.mappers.Views;
import ru.buzynnikov.json_view.models.UserOrder;
import ru.buzynnikov.json_view.services.interfaces.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @JsonView(Views.OrderDetails.class)
    @PostMapping("/{userId}")
    public ResponseEntity<UserOrder> createOrder(@RequestBody CreateOrderDto orderDto, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(orderService.create(orderDto, userId));
    }
}

