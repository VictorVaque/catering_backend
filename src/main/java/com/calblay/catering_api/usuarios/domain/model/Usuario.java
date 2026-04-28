package com.calblay.catering_api.usuarios.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {
    private Integer idUsuario;
    private String correo;
    private String password;
    private String nombre;
    private String rol;
}