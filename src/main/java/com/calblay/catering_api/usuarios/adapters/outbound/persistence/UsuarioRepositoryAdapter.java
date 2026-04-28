package com.calblay.catering_api.usuarios.adapters.outbound.persistence;

import com.calblay.catering_api.usuarios.application.port.out.UsuarioRepositoryPort;
import com.calblay.catering_api.usuarios.domain.model.Usuario;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Usuario> findByEmail(String correo) {
        Optional<UsuarioEntity> entityOptional = jpaRepository.findByEmail(correo);

        return entityOptional
                .map(entity -> new Usuario(entity.getIdUsuario(), entity.getEmail(), entity.getPassword(),
                        entity.getNombre(), entity.getRol()));
    }

    @Override
    public Optional<Usuario> getByUsuario(String usuario)
    {
        Optional<UsuarioEntity> user = jpaRepository.getByUsuario(usuario);

        return entityOptional
                .map(entity -> new Usuario
                (
                    entity.getIdUsuario(),
                    entity.getUsuario(),
                    entity.getEmail(),
                    entity.getNombre(),
                    entity.getRol()
                ));
    }
}