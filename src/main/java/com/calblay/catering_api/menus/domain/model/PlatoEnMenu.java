package com.calblay.catering_api.menus.domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// Modelo de dominio - plato dentro de un menú
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatoEnMenu {
    private Long idPlato;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private Integer orden;
    private String ordenEscrito;
}
