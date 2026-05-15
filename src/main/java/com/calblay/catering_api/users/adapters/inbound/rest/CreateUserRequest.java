package com.calblay.catering_api.users.adapters.inbound.rest;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String usuario;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene formato válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "La confirmación de contraseña es obligatoria")
    private String confirmPassword;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(
        regexp = "Comercial|Produccion|Administrador",
        message = "El rol debe ser: Comercial, Produccion o Administrador"
    )
    private String rol;
}