package com.calblay.catering_api.users.adapters.outbound.persistence;

import com.calblay.catering_api.users.application.port.out.UserRepositoryPort;
import com.calblay.catering_api.users.domain.model.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

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
                .map(entity -> new User(entity.getIdUsuario(), entity.getUsuario(), entity.getEmail(),
                        entity.getPassword(),
                        entity.getNombre(), entity.getRol()));
    }

    @Override
    public Optional<User> getByUsuario(String usuario) {
        Optional<UserEntity> user = jpaRepository.getByUsuario(usuario);

        return user.map(entity -> new User(
                entity.getIdUsuario(),
                entity.getUsuario(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getNombre(),
                entity.getRol()));
    }

    @Override
    public User save(User user) {

        UserEntity entity = new UserEntity();
        // Para crear un usuario no necesitamos utilizar
        // idUsuario, ya que se genera dinámicamente en la BD (autoincrement)
        entity.setUsuario(user.getUsuario());
        entity.setEmail(user.getCorreo());
        entity.setPassword(user.getPassword());
        entity.setNombre(user.getNombre());
        entity.setRol(user.getRol());

        UserEntity savedEntity = jpaRepository.save(entity);

        return new User(
                savedEntity.getIdUsuario(),
                savedEntity.getUsuario(),
                savedEntity.getEmail(),
                savedEntity.getPassword(),
                savedEntity.getNombre(),
                savedEntity.getRol());
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> entities = jpaRepository.findAll();

        return entities.stream()
                .map(entity -> new User(
                        entity.getIdUsuario(),
                        entity.getUsuario(),
                        entity.getEmail(),
                        entity.getPassword(),
                        entity.getNombre(),
                        entity.getRol()))
                .collect(Collectors.toList());
    }
}