package ru.buzynnikov.spring_object_mapper.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.buzynnikov.spring_object_mapper.exceptions.BadRequestException;
import ru.buzynnikov.spring_object_mapper.exceptions.FormatToJsonException;
import ru.buzynnikov.spring_object_mapper.exceptions.FormatToEntityException;
import ru.buzynnikov.spring_object_mapper.models.Product;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductMapper {

    private final ObjectMapper objectMapper;

    private ProductMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(Product product) {
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new FormatToJsonException("Ошибка преобразования в JSON. Детали: " + e.getMessage());
        }
    }

    public Product toProduct(String json) {
        try {
            Product product = objectMapper.readValue(json, Product.class);
            validateJson(product);
            return product;
        } catch (JsonProcessingException e) {
            throw new FormatToEntityException("Ошибка преобразования в Product. Детали: " + e.getMessage());
        }
    }

    public String toListJson(List<Product> products) {
        try {
            return objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            throw new FormatToJsonException("Ошибка преобразования в JSON. Детали: " + e.getMessage());
        }
    }

    private void validateJson(Product product) {
        if(product == null){
            throw new BadRequestException("Неверный формат JSON");
        }
        if(product.getName() == null){
            throw new BadRequestException("Не задано поле name");
        }
        if(product.getName().length() < 3 || product.getName().length() > 255){
            throw new BadRequestException("Поле name должно быть от 3 до 255 символов");
        }
        if(product.getPrice() == null){
            throw new BadRequestException("Не задано поле price");
        }
        if(product.getPrice().compareTo(BigDecimal.valueOf(0.01)) < 0){
            throw new BadRequestException("Поле price должно быть больше 0");
        }
        if(product.getQuantityStock() == null){
            throw new BadRequestException("Не задано поле quantityStock");
        }
        if(product.getQuantityStock() < 0){
            throw new BadRequestException("Поле quantityStock должно быть больше или равно 0");
        }
        if(product.getDescription() == null){
            throw new BadRequestException("Поле description должно быть задано");
        }
        if(product.getDescription().length() < 10 || product.getDescription().length() > 1000){
            throw new BadRequestException("Поле description должно быть от 10 до 1000 символов");
        }
    }
}
