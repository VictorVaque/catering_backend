package com.calblay.catering_api.events.adapters.outbound.persistence;

import com.calblay.catering_api.events.application.port.out.RecintoRepositoryPort;
import com.calblay.catering_api.events.domain.model.Recinto;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class RecintoRepositoryAdapter implements RecintoRepositoryPort {

    private final RecintoJpaRepository recintoJpaRepository;

    public RecintoRepositoryAdapter(RecintoJpaRepository recintoJpaRepository) {
        this.recintoJpaRepository = recintoJpaRepository;
    }

    @Override
    public Optional<Recinto> findById(Integer id) {
        return recintoJpaRepository.findById(id)
                .map(e -> new Recinto(e.getIdRecinto(), e.getNombre(), e.getUbicacion()));
    }
}