package com.calblay.catering_api.usuarios.adapters.inbound.rest;

import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
public class UpdateProfileRequest
{
    private String usuario; 
    private String nombre;    
    private String rol;    
    private String correo;
    private String password;
}