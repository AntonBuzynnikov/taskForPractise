package ru.buzynnikov.spring_object_mapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.buzynnikov.spring_object_mapper.exceptions.*;
import ru.buzynnikov.spring_object_mapper.mappers.ExceptionMapper;

@ControllerAdvice
public class GlobalExceptionController {

    private final ExceptionMapper exceptionMapper;

    public GlobalExceptionController(ExceptionMapper exceptionMapper) {
        this.exceptionMapper = exceptionMapper;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionMapper.mapException(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionMapper.mapException(e));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMapper.mapException(e));
    }
    @ExceptionHandler(FormatToJsonException.class)
    public ResponseEntity<String> handleFormatToJsonException(FormatToJsonException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionMapper.mapException(e));
    }
    @ExceptionHandler(FormatToEntityException.class)
    public ResponseEntity<String> handleFormatToProductException(FormatToEntityException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionMapper.mapException(e));
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionMapper.mapException(e));
    }
}
