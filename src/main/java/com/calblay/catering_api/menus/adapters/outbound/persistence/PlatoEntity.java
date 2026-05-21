package com.calblay.catering_api.menus.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "platos")
public class PlatoEntity {

    @Id
    @Column(name = "idplato")
    private Long idPlato;

    private String nombre;
    private String descripcion;

    @Column(name = "imagenurl")
    private String imagenUrl;

    private String temporada;
    private Double precio;
    private String origen;
}
