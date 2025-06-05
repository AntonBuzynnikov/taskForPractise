package ru.buzynnikov.spring_object_mapper.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.buzynnikov.spring_object_mapper.dto.CreateOrderDto;
import ru.buzynnikov.spring_object_mapper.exceptions.BadRequestException;
import ru.buzynnikov.spring_object_mapper.exceptions.FormatToJsonException;
import ru.buzynnikov.spring_object_mapper.exceptions.FormatToEntityException;
import ru.buzynnikov.spring_object_mapper.models.Order;

@Component
public class OrderMapper {

    private final ObjectMapper objectMapper;

    public OrderMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String mapOrderToString(Order order) {
        try {
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new FormatToJsonException("Ошибка преобразования в JSON. Детали: " + e.getMessage());
        }
    }

    public CreateOrderDto mapStringToOrder(String request) {
        try {
            CreateOrderDto createOrderDto = objectMapper.readValue(request, CreateOrderDto.class);
            validateCreateOrderDto(createOrderDto);
            return createOrderDto;
        } catch (JsonProcessingException e) {
            throw new FormatToEntityException("Ошибка преобразования в CreateOrderDto. Детали: " + e.getMessage());
        }
    }

    private void validateCreateOrderDto(CreateOrderDto createOrderDto) {
        if (createOrderDto == null) {
            throw new BadRequestException("Пустой CreateOrderDto");
        }
        if(createOrderDto.customerId() == null) {
            throw new BadRequestException("Пустой customerId");
        }
        if(createOrderDto.productIds().isEmpty()){
            throw new BadRequestException("Пустой список productId");
        }
        if(createOrderDto.shippingAddress() == null) {
            throw new BadRequestException("Пустой shippingAddress");
        }
        if(createOrderDto.shippingAddress().length() < 10 || createOrderDto.shippingAddress().length() > 255){
            throw new BadRequestException("Длина shippingAddress меньше 10 или больше 255 символов");
        }

    }
}
