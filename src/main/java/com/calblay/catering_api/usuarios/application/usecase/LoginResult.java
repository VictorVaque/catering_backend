package com.calblay.catering_api.usuarios.application.usecase;

public class LoginResult {
    private final String token;
    private final String rol;

    public LoginResult(String token, String rol) {
        this.token = token;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public String getRol() {
        return rol;
    }
}