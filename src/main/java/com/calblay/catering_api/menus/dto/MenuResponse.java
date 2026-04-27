package com.calblay.catering_api.menus.dto;

import com.calblay.catering_api.menus.enums.EstadoRegistro;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuResponse {
    private Long idMenu;
    private String nombre;
    private String imagenUrl;
    private EstadoRegistro estado;
}