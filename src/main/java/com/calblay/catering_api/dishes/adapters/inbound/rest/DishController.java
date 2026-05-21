package com.calblay.catering_api.dishes.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.dishes.application.usecase.GetDishInfoUseCase;
import com.calblay.catering_api.dishes.application.usecase.GetDishInfoResult;
import com.calblay.catering_api.dishes.application.usecase.UpdateDishUseCase;
import com.calblay.catering_api.dishes.application.usecase.DeleteDishUseCase;
import com.calblay.catering_api.dishes.application.usecase.CreateDishUseCase;
import com.calblay.catering_api.dishes.application.usecase.CreateDishResult;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dishes")
@CrossOrigin(origins = "*")
public class DishController {

    private final GetDishInfoUseCase getDishInfoUseCase;
    private final UpdateDishUseCase updateDishUseCase;
    private final DeleteDishUseCase deleteDishUseCase;
    private final CreateDishUseCase createDishUseCase;

    public DishController(
        GetDishInfoUseCase getDishInfoUseCase,
        UpdateDishUseCase updateDishUseCase,
        DeleteDishUseCase deleteDishUseCase,
        CreateDishUseCase createDishUseCase
    ) {
        this.getDishInfoUseCase = getDishInfoUseCase;
        this.updateDishUseCase = updateDishUseCase;
        this.deleteDishUseCase = deleteDishUseCase;
        this.createDishUseCase = createDishUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createDish(@RequestBody CreateDishRequest request) {

        try {

            CreateDishResult result = createDishUseCase.createDish(request);

            CreateDishResponse response = new CreateDishResponse(
                result.getIdPlato(),
                result.getNombre()
            );

            return ResponseEntity.status(201).body(response);

        } catch (RuntimeException e) {

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDish(@PathVariable("id") Integer id) {

        try {

            GetDishInfoResult result = getDishInfoUseCase.getDishInfo(id);

            GetDishInfoResponse response = new GetDishInfoResponse(
                result.getIdPlato(),
                result.getNombre(),
                result.getDescripcion(),
                result.getImagenUrl(),
                result.getTemporada(),
                result.getPrecio(),
                result.getOrigen(),

                result.getIngredientes().stream()
                    .map(i -> new DishIngredientResponse(
                        i.getIdIngrediente(),
                        i.getNombre(),
                        i.getUnidad(),
                        i.getEsOpcional(),
                        i.getCantidadRacion()
                    ))
                    .collect(Collectors.toList()),

                result.getAlergenos(),

                result.getUtensilios().stream()
                    .map(u -> new DishUtensilResponse(
                        u.getIdUtensilio(),
                        u.getTipo(),
                        u.getCocina(),
                        u.getCantidadMax(),
                        u.getPresentacion(),
                        u.getEstado()
                    ))
                    .collect(Collectors.toList()),

                result.getPasosPreparacion().stream()
                    .map(p -> new PreparationStepResponse(
                        p.getIdPaso(),
                        p.getOrden(),
                        p.getInstruccion(),
                        p.getTiempoEstimadoMin()
                    ))
                    .collect(Collectors.toList())
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDish(
        @PathVariable("id") Integer id,
        @RequestBody UpdateDishRequest request
    ) {

        try {

            updateDishUseCase.updateDish(id, request);

            return ResponseEntity.ok("Plato actualizado correctamente");

        } catch (RuntimeException e) {

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable("id") Integer id) {

        try {

            deleteDishUseCase.deleteDish(id);

            return ResponseEntity.ok("Plato eliminado correctamente");

        } catch (RuntimeException e) {

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
