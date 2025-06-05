package ru.buzynnikov.spring_object_mapper.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.buzynnikov.spring_object_mapper.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("UPDATE Product p SET p.quantityStock = p.quantityStock - 1 WHERE p.productId = :productId")
    @Modifying
    void subtractQuantity(Long productId);
}