package ru.buzynnikov.spring_mvc_library.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) { super(message);}
}
