package com.calblay.catering_api.ingredients.adapters.outbound.persistence;
import com.calblay.catering_api.ingredients.domain.model.IngredientStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ingredientes")
public class IngredientsEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idingrediente;
    
    private String nombre;
    
    @Column(name = "unidadmedidadefault")
    private String unidadmedidadefault;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private IngredientStatus estado;
    
    private Integer cantidadpaquete;
}