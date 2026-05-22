package com.calblay.catering_api.events.application.usecase;

import java.time.LocalDateTime;

public class GetEventInfoResult {
    private final String nombre;
    private final LocalDateTime fechahora;
    private final Integer idrecinto;
    private final Integer comensales;

    public GetEventInfoResult(String nombre, LocalDateTime fechahora, Integer idrecinto, Integer comensales)
    {
        this.nombre = nombre;
        this.fechahora = fechahora;
        this.idrecinto = idrecinto;
        this.comensales = comensales;
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
}