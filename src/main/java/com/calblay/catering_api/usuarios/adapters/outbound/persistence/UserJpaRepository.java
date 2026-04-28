package com.calblay.catering_api.usuarios.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> getByUsuario(String usuario);
}