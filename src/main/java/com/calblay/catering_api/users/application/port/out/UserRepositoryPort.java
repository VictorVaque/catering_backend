package com.calblay.catering_api.users.application.port.out;

import com.calblay.catering_api.users.domain.model.User;
import java.util.Optional;
import java.util.List;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);

    Optional<User> getByUsuario(String usuario);

    User save(User usuario);

    List<User> findAll();
}