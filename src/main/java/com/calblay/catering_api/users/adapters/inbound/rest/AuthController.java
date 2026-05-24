package com.calblay.catering_api.users.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.users.application.usecase.LoginUseCase;
import com.calblay.catering_api.users.application.usecase.LoginResult;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            LoginResult result = loginUseCase.execute(
                    request.getEmail(),
                    request.getPassword());

            AuthResponse response = new AuthResponse(result.getToken(), result.getRol());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}