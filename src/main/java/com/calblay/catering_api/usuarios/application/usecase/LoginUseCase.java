package com.calblay.catering_api.usuarios.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.usuarios.domain.model.Usuario;
import com.calblay.catering_api.usuarios.application.port.out.UsuarioRepositoryPort;

@Service
public class LoginUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public LoginUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    public LoginResult login(String email, String password) {

        Usuario usuario = usuarioRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email o contraseña incorrectos"));

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }
        String token = "generate_token"; // jwtUtil.generateToken(usuario.getEmail());

        return new LoginResult(token, usuario.getRol());
    }
}