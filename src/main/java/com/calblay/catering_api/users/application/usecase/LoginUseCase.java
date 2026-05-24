package com.calblay.catering_api.users.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.users.domain.model.User;
import com.calblay.catering_api.users.application.port.out.UserRepositoryPort;
import com.calblay.catering_api.users.application.port.out.TokenServicePort;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final TokenServicePort tokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginUseCase(UserRepositoryPort userRepositoryPort,
            TokenServicePort tokenService, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResult execute(String email, String password) {

        User usuario = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email o contraseña incorrectos"));

        // to do:
        // cambiar lógica para verificar la contraseña a:
        // passwordEncoder.matches(password, usuario.getPassword())
        // hay que cambiar las contraseñas de la BD para poder probar esto. Ya que si no
        // van, con el JWT no se puede hacer login

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        String token = tokenService.generate(usuario.getCorreo(), usuario.getRol());

        return new LoginResult(token, usuario.getRol());
    }
}