package com.calblay.catering_api.platos.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "platos")
public class PlatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idplato")
    private Integer idPlato;

    private String nombre;
    private String descripcion;

    @Column(name = "imagenurl")
    private String imagenUrl;

    private String temporada;
    private BigDecimal precio;
    private String origen;
}
