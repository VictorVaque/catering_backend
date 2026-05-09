package com.calblay.catering_api.usuarios.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProfileResponse
{
    private String usuario; 
    private String nombre;    
    private String rol;    
    private String correo;
    private String password;
}