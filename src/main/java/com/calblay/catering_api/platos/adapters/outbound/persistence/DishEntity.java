package com.calblay.catering_api.platos.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

// Entidad JPA que mapea la tabla Platos de la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "platos")
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plato")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String name;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "url_imagen", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "temporada", length = 25)
    private String season;

    @Column(name = "origen")
    private String origin;

    // Ingredientes del plato (tabla plato_ingredientes)
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishIngredientEntity> ingredients;

    // Pasos de preparación (tabla pasos_preparacion)
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreparationStepEntity> steps;
}
