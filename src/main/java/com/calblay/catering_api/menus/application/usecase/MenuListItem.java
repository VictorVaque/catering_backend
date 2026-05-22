package com.calblay.catering_api.menus.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

// Representa un menú individual dentro del resultado de la lista
@Data
@AllArgsConstructor
public class MenuListItem {
    private Integer idMenu;
    private Integer idCreador;
    private String nombre;
    private String imagenUrl;
    private LocalDateTime fechaCreacion;
    private String estado;
}
