package com.calblay.catering_api.requests.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.requests.application.port.out.RequestRepositoryPort;
import com.calblay.catering_api.requests.domain.model.Request;
import java.util.List;

@Service
public class GetRequestsListUseCase {

    private final RequestRepositoryPort requestRepositoryPort;

    public GetRequestsListUseCase(RequestRepositoryPort requestRepositoryPort) {
        this.requestRepositoryPort = requestRepositoryPort;
    }

    public GetRequestsListResult getRequestsList(Integer eventoId) {
        List<Request> requests = requestRepositoryPort.findByEventoId(eventoId);
        return new GetRequestsListResult(requests);
    }
}
