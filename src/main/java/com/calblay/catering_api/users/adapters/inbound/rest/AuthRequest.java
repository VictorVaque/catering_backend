package com.calblay.catering_api.users.adapters.inbound.rest;

import lombok.Data;
import lombok.NoArgsConstructor; //si no tiene el no args constructor no funciona el auth (no borrar pls)

@Data
@NoArgsConstructor
public class AuthRequest {
    private String email;
    private String password;

}