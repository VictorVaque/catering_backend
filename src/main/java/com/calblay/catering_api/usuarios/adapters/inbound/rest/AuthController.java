package com.calblay.catering_api.usuarios.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.usuarios.application.usecase.LoginUseCase;
import com.calblay.catering_api.usuarios.application.usecase.LoginResult;

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
            // 1. Datos puros de entrada
            String email = request.getEmail();
            String password = request.getPassword();

            // 2. El caso de uso nos devuelve los datos limpios (Token y Rol)
            LoginResult resultado = authService.login(email, password);

            // 3. ENVOLVEMOS los datos en el DTO web usando el @AllArgsConstructor
            AuthResponse response = new AuthResponse(resultado.getToken(), resultado.getRol());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}