package ru.buzynnikov.spring_object_mapper.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.spring_object_mapper.mappers.OrderMapper;
import ru.buzynnikov.spring_object_mapper.models.Order;
import ru.buzynnikov.spring_object_mapper.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<String> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderMapper.mapOrderToString(orderService.getOrderById(id)));
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody String request, UriComponentsBuilder builder) {
        Order order = orderService.createOrder(orderMapper.mapStringToOrder(request));
        return ResponseEntity.created(builder.path("/orders/{id}").buildAndExpand(order.getOrderId()).toUri()).body(orderMapper.mapOrderToString(order));
    }
}

