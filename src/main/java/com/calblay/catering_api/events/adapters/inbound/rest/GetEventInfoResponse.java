package com.calblay.catering_api.events.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetEventInfoResponse {
    
    private String nombre;
    
    private LocalDateTime fechahora;
    
    //En la BBDD para lo que es recinto o ubicación solamente tenemos el id del recinto, supongo que en otra parte se relacionara la ubicacion a partir del id. 
    private Integer idrecinto;
    private Integer comensales;
    private Integer idmenu;
    private String nombreRecinto;
    //Numero de comensales
    

}