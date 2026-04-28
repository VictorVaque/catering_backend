package com.calblay.catering_api.usuarios.adapters.outbound.persistence;

import com.calblay.catering_api.usuarios.application.port.out.UserRepositoryPort;
import com.calblay.catering_api.usuarios.domain.model.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<User> findByEmail(String correo) {
        Optional<UserEntity> entityOptional = jpaRepository.findByEmail(correo);

        return entityOptional
                .map(entity -> new User(entity.getIdUsuario(), entity.getUsuario(), entity.getEmail(), entity.getPassword(),
                        entity.getNombre(), entity.getRol()));
    }

    @Override
    public Optional<User> getByUsuario(String usuario)
    {
        Optional<UserEntity> user = jpaRepository.getByUsuario(usuario);

        return user.map(entity -> new User(
                    entity.getIdUsuario(),
                    entity.getUsuario(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getNombre(),
                    entity.getRol()
                ));
    }
}