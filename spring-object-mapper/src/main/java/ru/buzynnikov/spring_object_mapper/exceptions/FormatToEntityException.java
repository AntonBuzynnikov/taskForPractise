package ru.buzynnikov.spring_object_mapper.exceptions;

public class FormatToEntityException extends RuntimeException {
    public FormatToEntityException(String message) {
        super(message);
    }
}
