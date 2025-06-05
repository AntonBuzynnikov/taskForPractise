package ru.buzynnikov.spring_object_mapper.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.spring_object_mapper.dto.CreateOrderDto;
import ru.buzynnikov.spring_object_mapper.exceptions.OrderNotFoundException;
import ru.buzynnikov.spring_object_mapper.models.Order;
import ru.buzynnikov.spring_object_mapper.models.Product;
import ru.buzynnikov.spring_object_mapper.models.Status;
import ru.buzynnikov.spring_object_mapper.repositories.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final CustomerService customerService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Order createOrder(CreateOrderDto request) {
        Order order = new Order();
        List<Product> products = productService.getProductsByIds(request.productIds());
        for(Long id : request.productIds()) {
            productService.subtractProductQuantity(id);
        }
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customerService.findById(request.customerId()));
        order.setProducts(products);
        order.setStatus(Status.NEW);
        order.setShippingAddress(request.shippingAddress());
        order.setTotalPrice(products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Заказ с id " + id + " не найден"));
    }
}
