package com.calblay.catering_api.requests.application.usecase;

import com.calblay.catering_api.requests.application.port.out.RequestRepositoryPort;
import com.calblay.catering_api.requests.domain.model.Request;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllRequestsUseCase {

    private final RequestRepositoryPort requestRepositoryPort;

    public GetAllRequestsUseCase(RequestRepositoryPort requestRepositoryPort) {
        this.requestRepositoryPort = requestRepositoryPort;
    }

    public GetAllRequestsResult getAllRequests() {
        List<Request> requests = requestRepositoryPort.findAllWithEventDetails();
        return new GetAllRequestsResult(requests);
    }
}
