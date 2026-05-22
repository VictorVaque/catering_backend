package com.calblay.catering_api.utensils.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "utensilios") // <-- Nom real de la teva taula a la BD
public class UtensilEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Utilitzem IDENTITY per defecte si no teniu seqüències manuals
    @Column(name = "id") // <-- Nom exacte a la BD
    private Integer idUtensil;
    
    @Column(name = "nombre") // <-- Nom exacte a la BD
    private String name;
    
    @Column(name = "capacidad") // <-- Nom exacte a la BD
    private String capacity;
    
    @Column(name = "unidades") // <-- A la BD es diu 'unidades'
    private Integer quantity; // Mantenim la variable en anglès per al codi
}
