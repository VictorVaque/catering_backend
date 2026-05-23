package com.calblay.catering_api.users.application.port.out;

public interface TokenServicePort {
    String generate(String email, String role);

    String getEmail(String token);

    String getRole(String token);
}
