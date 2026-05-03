package com.calblay.catering_api.platos.adapters.inbound.rest;

import com.calblay.catering_api.platos.application.usecase.CreateDishUseCase;
import com.calblay.catering_api.platos.application.usecase.CreateDishResult;
import com.calblay.catering_api.platos.domain.model.Dish;
import com.calblay.catering_api.platos.domain.model.DishIngredient;
import com.calblay.catering_api.platos.domain.model.PreparationStep;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

// Controlador REST para la gestión de platos
@RestController
@RequestMapping("/api/platos")
@CrossOrigin(origins = "*")
public class DishController {

    private final CreateDishUseCase createDishUseCase;

    public DishController(CreateDishUseCase createDishUseCase) {
        this.createDishUseCase = createDishUseCase;
    }

    // Endpoint para crear un nuevo plato
    @PostMapping
    public ResponseEntity<?> createDish(@Valid @RequestBody CreateDishRequest request) {
        try {
            // Convertimos el request a dominio
            List<DishIngredient> ingredients = null;
            if (request.getIngredients() != null) {
                ingredients = request.getIngredients().stream()
                    .map(i -> new DishIngredient(i.getIngredientId(), i.getQuantity(), i.getUnit(), i.getOptional()))
                    .collect(Collectors.toList());
            }

            List<PreparationStep> steps = null;
            if (request.getSteps() != null) {
                steps = request.getSteps().stream()
                    .map(s -> new PreparationStep(s.getOrder(), s.getInstruction(), s.getEstimatedTimeMin()))
                    .collect(Collectors.toList());
            }

            Dish dish = new Dish(
                null,
                request.getName(),
                request.getDescription(),
                request.getImageUrl(),
                request.getSeason(),
                request.getOrigin(),
                ingredients,
                steps,
                request.getUtensils()
            );

            // Ejecutamos el caso de uso
            CreateDishResult result = createDishUseCase.execute(dish);

            // Devolvemos la respuesta con los datos del plato creado
            CreateDishResponse response = new CreateDishResponse(
                result.getId(),
                result.getName(),
                result.getDescription(),
                result.getImageUrl(),
                result.getSeason(),
                result.getOrigin()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            // Error genérico al crear el plato
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
