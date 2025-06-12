package ru.buzynnikov.order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.order_service.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
