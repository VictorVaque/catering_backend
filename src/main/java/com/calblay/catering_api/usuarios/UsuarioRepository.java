package com.calblay.catering_api.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring Boot escribirá la consulta SQL por nosotros automáticamente
    Optional<Usuario> findByEmail(String email);
}