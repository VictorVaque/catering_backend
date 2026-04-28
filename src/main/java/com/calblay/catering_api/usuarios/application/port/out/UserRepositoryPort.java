package com.calblay.catering_api.usuarios.application.port.out;

import com.calblay.catering_api.usuarios.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);

    Optional<User> getByUsuario(String usuario);
}