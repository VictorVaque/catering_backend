package com.calblay.catering_api.users.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserResult {
    private Integer idUsuario;
    private String usuario;
    private String correo;
    private String nombre;
    private String rol;
}
