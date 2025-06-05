package ru.buzynnikov.spring_object_mapper.services;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.buzynnikov.spring_object_mapper.dto.CreateOrderDto;
import ru.buzynnikov.spring_object_mapper.exceptions.OrderNotFoundException;
import ru.buzynnikov.spring_object_mapper.models.*;
import ru.buzynnikov.spring_object_mapper.repositories.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private CreateOrderDto createOrderDto;
    private Customer testCustomer;
    private Product testProduct1;
    private Product testProduct2;
    private Order testOrder;
    private final Long orderId = 1L;
    private final Long customerId = 1L;
    private final List<Long> productIds = List.of(1L, 2L);

    @BeforeEach
    void setUp() {
        // Setup test data
        createOrderDto = new CreateOrderDto(
                customerId,
                "Test Address",
                productIds
        );

        testCustomer = new Customer();
        testCustomer.setCustomerId(customerId);
        testCustomer.setFirstName("John");
        testCustomer.setLastName("Doe");

        testProduct1 = new Product();
        testProduct1.setProductId(1L);
        testProduct1.setName("Product 1");
        testProduct1.setPrice(BigDecimal.valueOf(100.0));

        testProduct2 = new Product();
        testProduct2.setProductId(2L);
        testProduct2.setName("Product 2");
        testProduct2.setPrice(BigDecimal.valueOf(200.0));

        testOrder = new Order();
        testOrder.setOrderId(orderId);
        testOrder.setCustomer(testCustomer);
        testOrder.setProducts(List.of(testProduct1, testProduct2));
        testOrder.setOrderDate(LocalDate.now());
        testOrder.setShippingAddress("Test Address");
        testOrder.setTotalPrice(BigDecimal.valueOf(300.0));
        testOrder.setStatus(Status.NEW);
    }

    @Test
    @Transactional
    void createOrder_ShouldCreateNewOrder() {
        // Arrange
        when(productService.getProductsByIds(productIds))
                .thenReturn(List.of(testProduct1, testProduct2));
        when(customerService.findById(customerId))
                .thenReturn(testCustomer);
        when(orderRepository.save(any(Order.class)))
                .thenReturn(testOrder);

        // Act
        Order createdOrder = orderService.createOrder(createOrderDto);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(testCustomer, createdOrder.getCustomer());
        assertEquals(2, createdOrder.getProducts().size());
        assertEquals(LocalDate.now(), createdOrder.getOrderDate());
        assertEquals("Test Address", createdOrder.getShippingAddress());
        assertEquals(Status.NEW, createdOrder.getStatus());
        assertEquals(BigDecimal.valueOf(300.0), createdOrder.getTotalPrice());

        // Verify interactions
        verify(productService, times(1)).getProductsByIds(productIds);
        verify(productService, times(1)).subtractProductQuantity(1L);
        verify(productService, times(1)).subtractProductQuantity(2L);
        verify(customerService, times(1)).findById(customerId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @Transactional
    void createOrder_ShouldCalculateCorrectTotalPrice() {
        // Arrange
        when(productService.getProductsByIds(productIds))
                .thenReturn(List.of(testProduct1, testProduct2));
        when(customerService.findById(customerId))
                .thenReturn(testCustomer);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order createdOrder = orderService.createOrder(createOrderDto);

        // Assert
        assertEquals(
                testProduct1.getPrice().add(testProduct2.getPrice()),
                createdOrder.getTotalPrice()
        );
    }

    @Test
    void getOrderById_WhenOrderExists_ShouldReturnOrder() {
        // Arrange
        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(testOrder));

        // Act
        Order foundOrder = orderService.getOrderById(orderId);

        // Assert
        assertNotNull(foundOrder);
        assertEquals(orderId, foundOrder.getOrderId());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void getOrderById_WhenOrderNotExists_ShouldThrowException() {
        // Arrange
        when(orderRepository.findById(orderId))
                .thenReturn(Optional.empty());

        // Act & Assert
        OrderNotFoundException exception = assertThrows(
                OrderNotFoundException.class,
                () -> orderService.getOrderById(orderId)
        );

        assertEquals("Заказ с id " + orderId + " не найден", exception.getMessage());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    @Transactional
    void createOrder_ShouldSetCorrectStatus() {
        // Arrange
        when(productService.getProductsByIds(productIds))
                .thenReturn(List.of(testProduct1, testProduct2));
        when(customerService.findById(customerId))
                .thenReturn(testCustomer);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order createdOrder = orderService.createOrder(createOrderDto);

        // Assert
        assertEquals(Status.NEW, createdOrder.getStatus());
    }

    @Test
    @Transactional
    void createOrder_ShouldSetCurrentDate() {
        // Arrange
        when(productService.getProductsByIds(productIds))
                .thenReturn(List.of(testProduct1, testProduct2));
        when(customerService.findById(customerId))
                .thenReturn(testCustomer);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order createdOrder = orderService.createOrder(createOrderDto);

        // Assert
        assertEquals(LocalDate.now(), createdOrder.getOrderDate());
    }
}