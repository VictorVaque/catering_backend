package com.calblay.catering_api.requests.adapters.inbound.rest;

import jakarta.validation.constraints.NotBlank;

public class RequestStatusUpdate {
    @NotBlank(message = "el campo estado es obligatorio")
    private String status;

    public RequestStatusUpdate() {
    }

    public RequestStatusUpdate(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
