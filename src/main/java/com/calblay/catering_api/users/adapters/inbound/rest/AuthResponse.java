package com.calblay.catering_api.users.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// @AllArgsConstructor
public class AuthResponse {
    private String token;
    private String rol;

    public AuthResponse(String token, String rol) {
        this.token = token;
        this.rol = rol;
    }
}