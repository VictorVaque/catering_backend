package com.calblay.catering_api.usuarios;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}