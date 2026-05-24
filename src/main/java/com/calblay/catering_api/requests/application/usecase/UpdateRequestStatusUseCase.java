package com.calblay.catering_api.requests.application.usecase;

import com.calblay.catering_api.requests.domain.model.Request;
import com.calblay.catering_api.requests.application.port.out.RequestRepositoryPort;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class UpdateRequestStatusUseCase {
    private final RequestRepositoryPort requestRepository;

    public UpdateRequestStatusUseCase(RequestRepositoryPort requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Transactional
    public void updateStatus(Integer idPeticion, String nuevoEstado) {
        if (!"Confirmado".equalsIgnoreCase(nuevoEstado) && !"Cancelado".equalsIgnoreCase(nuevoEstado)) {
            throw new IllegalArgumentException("argumento invalido");
        }
        requestRepository.updateStatus(idPeticion, nuevoEstado);
    }
}
