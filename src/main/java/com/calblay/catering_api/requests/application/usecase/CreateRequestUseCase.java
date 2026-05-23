package com.calblay.catering_api.requests.application.usecase;

import com.calblay.catering_api.requests.adapters.inbound.rest.CreateRequestRequest;
import com.calblay.catering_api.requests.application.port.out.RequestRepositoryPort;
import com.calblay.catering_api.requests.domain.model.Request;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateRequestUseCase {

    private final RequestRepositoryPort requestRepositoryPort;

    public CreateRequestUseCase(RequestRepositoryPort requestRepositoryPort) {
        this.requestRepositoryPort = requestRepositoryPort;
    }

    public CreateRequestResult createRequest(CreateRequestRequest request) {

        if (request.getTitulo() == null || request.getTitulo().isBlank()) {
            throw new RuntimeException("El título de la petición es obligatorio");
        }

        if (request.getDescripcion() == null || request.getDescripcion().isBlank()) {
            throw new RuntimeException("La descripción de la petición es obligatoria");
        }

        if (request.getTipo() == null || request.getTipo().isBlank()) {
            throw new RuntimeException("El tipo de petición es obligatorio");
        }

        if (request.getCreador() == null) {
            throw new RuntimeException("El creador de la petición es obligatorio");
        }

        Request newRequest = new Request(
                null,
                request.getResponsable(),
                request.getCreador(),
                LocalDateTime.now(),
                "Pendiente",
                request.getTitulo(),
                request.getDescripcion(),
                request.getTipo()
        );

        Request savedRequest = requestRepositoryPort.saveRequest(newRequest);

        return new CreateRequestResult(
                "Petición creada correctamente",
                savedRequest.getIdPeticion()
        );
    }
}