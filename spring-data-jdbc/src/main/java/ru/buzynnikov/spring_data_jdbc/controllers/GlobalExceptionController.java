package ru.buzynnikov.spring_data_jdbc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.buzynnikov.spring_data_jdbc.dto.ExceptionResponse;
import ru.buzynnikov.spring_data_jdbc.exceptions.BadRequestException;
import ru.buzynnikov.spring_data_jdbc.exceptions.BookNotFoundException;

@ControllerAdvice
public class GlobalExceptionController {


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage()));
    }
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(BookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(e.getMessage()));
    }
}
