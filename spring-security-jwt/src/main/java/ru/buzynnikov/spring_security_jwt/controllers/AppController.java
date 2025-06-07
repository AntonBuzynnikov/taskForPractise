package ru.buzynnikov.spring_security_jwt.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.buzynnikov.spring_security_jwt.dto.LoginRequest;
import ru.buzynnikov.spring_security_jwt.dto.LoginResponse;
import ru.buzynnikov.spring_security_jwt.dto.RefreshRequest;
import ru.buzynnikov.spring_security_jwt.dto.RefreshResponse;
import ru.buzynnikov.spring_security_jwt.security.CustomerDetailsService;
import ru.buzynnikov.spring_security_jwt.security.JWTUtils;

@RestController
@RequestMapping("/security")
public class AppController {

    private final PasswordEncoder passwordEncoder;
    private final CustomerDetailsService customerDetailsService;
    private final JWTUtils jwtUtils;

    public AppController(PasswordEncoder passwordEncoder, CustomerDetailsService customerDetailsService, JWTUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.customerDetailsService = customerDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = customerDetailsService.loadUserByUsername(loginRequest.username());
        if (passwordEncoder.matches(loginRequest.password(), userDetails.getPassword())) {
            final String accessToken = jwtUtils.generateToken(userDetails);
            final String refreshToken = jwtUtils.generateRefreshToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
        } else {
            customerDetailsService.increaseFailedAttempts(loginRequest.username());
            throw new RuntimeException("Wrong password");
        }
    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest loginRequest) {
        String username = jwtUtils.extractUsername(loginRequest.refreshToken());
        System.out.println(loginRequest.refreshToken());
        if(jwtUtils.isTokenExpired(loginRequest.refreshToken())){
            UserDetails userDetails = customerDetailsService.loadUserByUsername(username);
            final String accessToken = jwtUtils.generateToken(userDetails);
            System.out.println("Access token generated: "+accessToken);
            return ResponseEntity.ok(new RefreshResponse(accessToken));
        }
        throw new RuntimeException("Refresh token is expired!");
    }
    @GetMapping("/authenticate")
    public ResponseEntity<String> authenticate() {
        return ResponseEntity.ok("Пользователь успешно аутентифицирован!");
    }
    @GetMapping("/moderator")
    public ResponseEntity<String> moderator() {
        return ResponseEntity.ok("Пользователь является модератором!");
    }
    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Пользователь является администратором!");
    }
}
