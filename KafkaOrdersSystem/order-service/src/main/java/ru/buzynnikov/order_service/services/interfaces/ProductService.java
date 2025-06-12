package ru.buzynnikov.order_service.services.interfaces;

import ru.buzynnikov.order_service.models.Product;
import ru.buzynnikov.order_service.models.Status;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> findAllById(List<Long> ids);


}
