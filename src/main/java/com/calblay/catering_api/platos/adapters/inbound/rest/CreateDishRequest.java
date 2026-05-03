package com.calblay.catering_api.platos.adapters.inbound.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

// Objeto que recibe el frontend cuando crea un plato
@Data
public class CreateDishRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotBlank(message = "La URL de imagen es obligatoria")
    private String imageUrl;

    private String season;
    private String origin;

    // Lista de ingredientes con cantidad y unidad
    private List<IngredientRequest> ingredients;

    // Pasos de preparación
    private List<StepRequest> steps;

    // Utensilios necesarios
    private List<String> utensils;
}
