package com.calblay.catering_api.users.application.usecase;

public class GetProfileResult {
    private final String nombre;
    private final String usuario;
    private final String rol;
    private final String correo;

    public GetProfileResult(String nombre, String usuario,String rol, String correo)
    {
        this.nombre = nombre;
        this.usuario = usuario;
        this.rol = rol;
        this.correo = correo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getUsuario()
    {
        return usuario;
    }

    public String getRol()
    {
        return rol;
    }

    public String getCorreo()
    {
        return correo;
    }
}