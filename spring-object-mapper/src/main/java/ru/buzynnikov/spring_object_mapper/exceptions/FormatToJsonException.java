package ru.buzynnikov.spring_object_mapper.exceptions;

public class FormatToJsonException extends RuntimeException {
    public FormatToJsonException(String message) {
        super(message);
    }
}
