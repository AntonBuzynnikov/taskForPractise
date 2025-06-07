package ru.buzynnikov.spring_security_jwt.dto;

public record LoginResponse(String accessToken, String refreshToken) {


}
