package ru.buzynnikov.spring_object_mapper.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.buzynnikov.spring_object_mapper.exceptions.ProductNotFoundException;
import ru.buzynnikov.spring_object_mapper.models.Product;
import ru.buzynnikov.spring_object_mapper.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;
    private final Long productId = 1L;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setProductId(productId);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(BigDecimal.valueOf(100.0));
        testProduct.setQuantityStock(10);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        // Arrange
        List<Product> expectedProducts = Collections.singletonList(testProduct);
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.getAllProducts();

        // Assert
        assertEquals(1, actualProducts.size());
        assertEquals(testProduct, actualProducts.get(0));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void createProduct_ShouldSaveAndReturnProduct() {
        // Arrange
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        // Act
        Product createdProduct = productService.createProduct(testProduct);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(testProduct.getName(), createdProduct.getName());
        assertEquals(testProduct.getDescription(), createdProduct.getDescription());
        assertEquals(testProduct.getPrice(), createdProduct.getPrice());
        assertEquals(testProduct.getQuantityStock(), createdProduct.getQuantityStock());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        // Act
        Product foundProduct = productService.getProductById(productId);

        // Assert
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getProductId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductById_WhenProductNotExists_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(productId));

        assertEquals("Продукт с id = " + productId + " не найден", exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void updateProduct_ShouldUpdateExistingProduct() {
        // Arrange
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(BigDecimal.valueOf(150.0));
        updatedProduct.setQuantityStock(5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        productService.updateProduct(updatedProduct, productId);

        // Assert
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(updatedProduct);
        assertEquals(productId, updatedProduct.getProductId());
    }

    @Test
    void updateProduct_WhenProductNotExists_ShouldThrowException() {
        // Arrange
        Product updatedProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(updatedProduct, productId));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    void deleteProductById_ShouldCallRepositoryDelete() {
        // Arrange
        doNothing().when(productRepository).deleteById(productId);

        // Act
        productService.deleteProductById(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void getProductsByIds_ShouldReturnProductsForGivenIds() {
        // Arrange
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<Product> expectedProducts = Collections.singletonList(testProduct);
        when(productRepository.findAllById(ids)).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.getProductsByIds(ids);

        // Assert
        assertEquals(1, actualProducts.size());
        assertEquals(testProduct, actualProducts.get(0));
        verify(productRepository, times(1)).findAllById(ids);
    }

    @Test
    void subtractProductQuantity_ShouldCallRepositorySubtractQuantity() {
        // Arrange
        doNothing().when(productRepository).subtractQuantity(productId);

        // Act
        productService.subtractProductQuantity(productId);

        // Assert
        verify(productRepository, times(1)).subtractQuantity(productId);
    }

}