package com.calblay.catering_api.requests.application.usecase;

import com.calblay.catering_api.requests.domain.model.Request;
import java.util.List;

public class GetAllRequestsResult {

    private final List<Request> requests;

    public GetAllRequestsResult(List<Request> requests) {
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }
}
