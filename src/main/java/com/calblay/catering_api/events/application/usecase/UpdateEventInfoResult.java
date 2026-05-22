package com.calblay.catering_api.events.application.usecase;

import java.time.LocalDateTime;

public class UpdateEventInfoResult
{
    private final Long idEvento;
    private final String nombre;
    private final LocalDateTime fechahora;
    private final Integer idrecinto;
    private final Integer comensales;

    public UpdateEventInfoResult(Long idEvento, String nombre, LocalDateTime fechahora, Integer idrecinto, Integer comensales)
    {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fechahora = fechahora;
        this.idrecinto = idrecinto;
        this.comensales = comensales;
    }

    public Long getIdEvento()
    {
        return idEvento;
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