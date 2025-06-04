package ru.buzynnikov.json_view.repositoies;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.buzynnikov.json_view.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}