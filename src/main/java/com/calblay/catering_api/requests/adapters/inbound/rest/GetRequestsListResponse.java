package com.calblay.catering_api.requests.adapters.inbound.rest;

import java.util.List;

public class GetRequestsListResponse {

    private List<RequestSummaryResponse> requests;

    public GetRequestsListResponse(List<RequestSummaryResponse> requests) {
        this.requests = requests;
    }

    public List<RequestSummaryResponse> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestSummaryResponse> requests) {
        this.requests = requests;
    }
}
