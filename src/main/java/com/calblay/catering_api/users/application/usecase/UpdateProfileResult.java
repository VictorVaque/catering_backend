package com.calblay.catering_api.users.application.usecase;

public class UpdateProfileResult
{
    private String nombre;
    private String usuario;
    private String rol;
    private String correo;
    private String password;

    public UpdateProfileResult(String nombre, String usuario, String rol, String correo, String password)
    {
        this.nombre = nombre;
        this.usuario = usuario;
        this.rol = rol;
        this.correo = correo;
        this.password = password;
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

    public String getPassword()
    {
        return password;
    }
}