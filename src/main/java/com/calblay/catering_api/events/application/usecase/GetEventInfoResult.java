package com.calblay.catering_api.events.application.usecase;

import java.time.LocalDateTime;

public class GetEventInfoResult {
    private final String nombre;
    private final LocalDateTime fechahora;
    private final Integer idrecinto;
    private final Integer comensales;
    private final Integer idmenu;
    private final String nombreRecinto; 

    public GetEventInfoResult(String nombre, LocalDateTime fechahora, Integer idrecinto, Integer comensales, Integer idmenu, String nombreRecinto)
    {
        this.nombre = nombre;
        this.fechahora = fechahora;
        this.idrecinto = idrecinto;
        this.comensales = comensales;
        this.idmenu = idmenu;
        this.nombreRecinto = nombreRecinto;
    }

    public String getNombre()
    {
        return nombre;
    }

    public LocalDateTime getFechahora()
    {
        return fechahora;
    }

    public Integer getIdrecinto()
    {
        return idrecinto;
    }

    public Integer getComensales()
    {
        return comensales;
    }

    public Integer getIdmenu() { return idmenu; }
    public String getNombreRecinto() { return nombreRecinto; }
}