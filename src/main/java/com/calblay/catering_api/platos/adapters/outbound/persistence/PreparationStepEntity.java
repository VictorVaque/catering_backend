package com.calblay.catering_api.platos.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Entidad JPA que mapea la tabla pasos_preparacion
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pasos_preparacion")
public class PreparationStepEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paso")
    private Long id;

    // Relación con el plato
    @ManyToOne
    @JoinColumn(name = "id_plato")
    private DishEntity dish;

    // Usamos "stepOrder" para evitar conflicto con palabra reservada ORDER en SQL
    @Column(name = "orden", nullable = false)
    private Integer stepOrder;

    @Column(name = "instruccion", nullable = false, columnDefinition = "TEXT")
    private String instruction;

    @Column(name = "tiempo_estimado_min", nullable = false)
    private Integer estimatedTimeMin;
}
