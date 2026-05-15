package com.calblay.catering_api.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Integer idUsuario;

    private String usuario;
    private String correo;
    private String password;
    private String nombre;
    private String rol;
}