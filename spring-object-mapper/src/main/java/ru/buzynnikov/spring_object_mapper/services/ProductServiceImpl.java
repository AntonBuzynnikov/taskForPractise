package ru.buzynnikov.spring_object_mapper.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.spring_object_mapper.exceptions.ProductNotFoundException;
import ru.buzynnikov.spring_object_mapper.models.Product;
import ru.buzynnikov.spring_object_mapper.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Продукт с id = " + id + " не найден"));
    }

    @Override
    public void updateProduct(Product request, Long productId) {
        Product product = getProductById(productId);
        request.setProductId(product.getProductId());
        productRepository.save(request);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductsByIds(List<Long> productIds) {
        return (List<Product>) productRepository.findAllById(productIds);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void subtractProductQuantity(Long productId) {
        productRepository.subtractQuantity(productId);
    }
}
