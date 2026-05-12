package com.calblay.catering_api.users.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.users.application.usecase.LoginUseCase;
import com.calblay.catering_api.users.application.usecase.LoginResult;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final LoginUseCase authService;
    public AuthController(LoginUseCase authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            String email = request.getEmail();
            String password = request.getPassword();

            LoginResult resultado = authService.login(email, password);
            AuthResponse response = new AuthResponse(resultado.getToken(), resultado.getRol());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}