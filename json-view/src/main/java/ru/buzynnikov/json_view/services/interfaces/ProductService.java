package ru.buzynnikov.json_view.services.interfaces;

import ru.buzynnikov.json_view.models.Product;

public interface ProductService {

    Product getProductById(Long id);
}
