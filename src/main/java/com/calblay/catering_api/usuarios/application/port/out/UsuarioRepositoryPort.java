package com.calblay.catering_api.usuarios.application.port.out;

import com.calblay.catering_api.usuarios.domain.model.Usuario;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsuario(String usuario);
}