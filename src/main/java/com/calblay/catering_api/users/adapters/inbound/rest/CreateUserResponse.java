package com.calblay.catering_api.users.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserResponse {
    private Integer idUsuario;
    private String usuario;
    private String correo;
    private String nombre;
    private String rol;
}