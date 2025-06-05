package ru.buzynnikov.spring_object_mapper.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.buzynnikov.spring_object_mapper.exceptions.*;
import ru.buzynnikov.spring_object_mapper.mappers.ExceptionMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionControllerTest {

    @Mock
    private ExceptionMapper exceptionMapper;

    @InjectMocks
    private GlobalExceptionController globalExceptionController;

    private final String errorMessage = "Test error message";
    private final String mappedMessage = "Mapped error message";

    @BeforeEach
    void setUp() {
        when(exceptionMapper.mapException(any(Exception.class))).thenReturn(mappedMessage);
    }

    @Test
    void handleProductNotFoundException_ShouldReturnNotFoundStatus() {
        // Arrange
        ProductNotFoundException exception = new ProductNotFoundException(errorMessage);

        // Act
        ResponseEntity<String> response = globalExceptionController.handleProductNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(mappedMessage, response.getBody());
        verify(exceptionMapper).mapException(exception);
    }

    @Test
    void handleCustomerNotFoundException_ShouldReturnNotFoundStatus() {
        // Arrange
        CustomerNotFoundException exception = new CustomerNotFoundException(errorMessage);

        // Act
        ResponseEntity<String> response = globalExceptionController.handleCustomerNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(mappedMessage, response.getBody());
        verify(exceptionMapper).mapException(exception);
    }

    @Test
    void handleBadRequestException_ShouldReturnBadRequestStatus() {
        // Arrange
        BadRequestException exception = new BadRequestException(errorMessage);

        // Act
        ResponseEntity<String> response = globalExceptionController.handleBadRequestException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(mappedMessage, response.getBody());
        verify(exceptionMapper).mapException(exception);
    }

    @Test
    void handleFormatToJsonException_ShouldReturnForbiddenStatus() {
        // Arrange
        FormatToJsonException exception = new FormatToJsonException(errorMessage);

        // Act
        ResponseEntity<String> response = globalExceptionController.handleFormatToJsonException(exception);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(mappedMessage, response.getBody());
        verify(exceptionMapper).mapException(exception);
    }

    @Test
    void handleFormatToEntityException_ShouldReturnForbiddenStatus() {
        // Arrange
        FormatToEntityException exception = new FormatToEntityException(errorMessage);

        // Act
        ResponseEntity<String> response = globalExceptionController.handleFormatToProductException(exception);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(mappedMessage, response.getBody());
        verify(exceptionMapper).mapException(exception);
    }

    @Test
    void handleGenericException_ShouldReturnForbiddenStatus() {
        // Arrange
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<String> response = globalExceptionController.handleException(exception);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(mappedMessage, response.getBody());
        verify(exceptionMapper).mapException(exception);
    }

    @Test
    void handleException_ShouldCallMapperForUnknownException() {
        // Arrange
        RuntimeException unknownException = new RuntimeException("Unknown error");

        // Act
        ResponseEntity<String> response = globalExceptionController.handleException(unknownException);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(mappedMessage, response.getBody());
        verify(exceptionMapper).mapException(unknownException);
    }
}