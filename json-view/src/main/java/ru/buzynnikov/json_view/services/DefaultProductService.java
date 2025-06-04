package ru.buzynnikov.json_view.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.json_view.exceptions.ProductNotFoundException;
import ru.buzynnikov.json_view.models.Product;
import ru.buzynnikov.json_view.repositoies.ProductRepository;
import ru.buzynnikov.json_view.services.interfaces.ProductService;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found"));
    }
}
