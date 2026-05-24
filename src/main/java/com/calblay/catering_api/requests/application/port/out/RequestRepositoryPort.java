package com.calblay.catering_api.requests.application.port.out;

import com.calblay.catering_api.requests.domain.model.Request;
import java.util.List;
import java.util.Optional;

public interface RequestRepositoryPort {
    List<Request> findByEventoId(Integer idEvento);

    List<Request> findAllWithEventDetails();

    Optional<Request> findByIdPeticion(Integer idPeticion);
    Request saveRequest(Request request);

    void updateStatus(Integer idPeticion, String nuevoEstado);

}
