package com.calblay.catering_api.ingredients.adapters.inbound.rest;

import com.calblay.catering_api.ingredients.application.usecase.GetIngredientsUseCase;
import com.calblay.catering_api.ingredients.application.usecase.GetIngredientsResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ingredients")
@CrossOrigin(origins = "*")
public class IngredientsController
{

    private final GetIngredientsUseCase getIngredientsUseCase;

    public IngredientsController(GetIngredientsUseCase getIngredientsUseCase)
    {
        this.getIngredientsUseCase = getIngredientsUseCase;
    }

    @GetMapping //ENDPOINT:   /api/ingredients/
    public ResponseEntity<List<GetIngredientsResponse>> getAllIngredients()
    {
        // Ejecutamos el caso de uso para obtener los resultados ordenados alfabéticamente
        List<GetIngredientsResult> resultados = getIngredientsUseCase.execute();

        // Mapeamos cada resultado directamente a nuestro GetIngredientsResponse
        List<GetIngredientsResponse> response = resultados.stream()
                .map(r -> new GetIngredientsResponse(
                        r.getIdingrediente(),
                        r.getNombre(),
                        r.getUnidadmedidadefault(),
                        r.getEstado(),
                        r.getCantidadpaquete()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}