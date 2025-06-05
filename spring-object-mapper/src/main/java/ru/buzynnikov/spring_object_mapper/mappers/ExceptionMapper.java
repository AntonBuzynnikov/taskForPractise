package ru.buzynnikov.spring_object_mapper.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.buzynnikov.spring_object_mapper.dto.ExceptionDto;

@Component
public class ExceptionMapper {

    private final ObjectMapper objectMapper;

    public ExceptionMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String mapException(Exception e) {
        try {
            return objectMapper.writeValueAsString(new ExceptionDto(e.getMessage()));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
