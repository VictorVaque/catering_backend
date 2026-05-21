package com.calblay.catering_api.dishes.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.math.BigDecimal; // Para Double -- BigDecimal

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "platos")
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idplato")
    private Integer idPlato; // Long -- Integer

    private String nombre;
    private String descripcion;

    @Column(name = "imagenurl")
    private String imagenUrl;

    private String temporada;
    private BigDecimal precio; // Double -- BigDecimal
    private String origen;
}