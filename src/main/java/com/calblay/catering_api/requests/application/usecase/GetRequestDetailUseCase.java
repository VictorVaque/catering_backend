package com.calblay.catering_api.requests.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.requests.application.port.out.RequestRepositoryPort;

@Service
public class GetRequestDetailUseCase {

    private final RequestRepositoryPort requestRepositoryPort;

    public GetRequestDetailUseCase(RequestRepositoryPort requestRepositoryPort) {
        this.requestRepositoryPort = requestRepositoryPort;
    }

    public GetRequestDetailResult getRequestDetail(Integer idPeticion) {
        return new GetRequestDetailResult(requestRepositoryPort.findByIdPeticion(idPeticion).orElse(null));
    }
}
