package com.calblay.catering_api.platos.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Entidad JPA que mapea la tabla plato_ingredientes
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plato_ingredientes")
public class DishIngredientEntity {

    // Clave primaria compuesta (id_plato + id_ingrediente)
    @EmbeddedId
    private DishIngredientId id;

    @ManyToOne
    @MapsId("dishId")
    @JoinColumn(name = "id_plato")
    private DishEntity dish;

    @Column(name = "unidad")
    private String unit;

    @Column(name = "es_opcional")
    private Boolean optional;

    @Column(name = "cantidad_racion")
    private Double quantity;
}
