package ru.buzynnikov.spring_object_mapper.services;

import ru.buzynnikov.spring_object_mapper.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product getProductById(Long id);

    void updateProduct(Product request, Long productId);

    void deleteProductById(Long id);

    List<Product> getProductsByIds(List<Long> productIds);

    void subtractProductQuantity(Long productId);
}
