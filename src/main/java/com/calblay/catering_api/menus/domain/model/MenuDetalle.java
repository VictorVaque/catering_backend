package com.calblay.catering_api.menus.domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

// Modelo de dominio - menú con sus platos
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDetalle {
    private Long idMenu;
    private Long idCreador;
    private String nombre;
    private String imagenUrl;
    private LocalDateTime fechaCreacion;
    private String estado;
    private List<PlatoEnMenu> platos;
}
