package com.calblay.catering_api.users.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.users.domain.model.User;
import com.calblay.catering_api.users.application.port.out.UserRepositoryPort;

@Service
public class LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public LoginUseCase(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public LoginResult login(String email, String password) {

        User usuario = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email o contraseña incorrectos"));

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }
        String token = "generate_token"; // jwtUtil.generateToken(usuario.getEmail());

        return new LoginResult(token, usuario.getRol());
    }
}