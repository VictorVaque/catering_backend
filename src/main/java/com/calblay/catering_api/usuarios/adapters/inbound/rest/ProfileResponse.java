package com.calblay.catering_api.usuarios.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {
    private String usuario;
    private String correo;
    private String nombre;
    private String rol;
}