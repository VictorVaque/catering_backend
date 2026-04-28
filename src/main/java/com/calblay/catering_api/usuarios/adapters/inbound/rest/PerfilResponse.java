package com.calblay.catering_api.usuarios.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerfilResponse {
    private String usuario;
    private String correo;
    private String nombre;
    private String rol;
}