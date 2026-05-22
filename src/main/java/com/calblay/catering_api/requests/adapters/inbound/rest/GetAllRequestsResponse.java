package com.calblay.catering_api.requests.adapters.inbound.rest;

import java.util.List;

public class GetAllRequestsResponse {

    private List<RequestWithEventResponse> requests;

    public GetAllRequestsResponse(List<RequestWithEventResponse> requests) {
        this.requests = requests;
    }

    public List<RequestWithEventResponse> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestWithEventResponse> requests) {
        this.requests = requests;
    }
}
