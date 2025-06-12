package ru.buzynnikov.order_service.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.order_service.models.Product;
import ru.buzynnikov.order_service.repositories.ProductRepository;
import ru.buzynnikov.order_service.services.interfaces.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllById(List<Long> ids) {
        return productRepository.findAllById(ids);
    }
}
