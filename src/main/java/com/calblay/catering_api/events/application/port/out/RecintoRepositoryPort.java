package com.calblay.catering_api.events.application.port.out;

import com.calblay.catering_api.events.domain.model.Recinto;
import java.util.Optional;

public interface RecintoRepositoryPort {
    Optional<Recinto> findById(Integer id);
}