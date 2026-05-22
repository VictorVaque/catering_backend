package com.calblay.catering_api.requests.application.usecase;

import com.calblay.catering_api.requests.domain.model.Request;
import java.util.Optional;

public class GetRequestDetailResult {
    private final Request request;

    public GetRequestDetailResult(Request request) {
        this.request = request;
    }

    public Optional<Request> getRequest() {
        return Optional.ofNullable(request);
    }
}
