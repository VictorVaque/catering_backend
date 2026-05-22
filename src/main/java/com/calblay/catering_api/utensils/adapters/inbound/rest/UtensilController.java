package com.calblay.catering_api.utensils.adapters.inbound.rest;

import com.calblay.catering_api.utensils.application.usecase.CreateUtensilUseCase;
import com.calblay.catering_api.utensils.application.usecase.DeleteUtensilUseCase;
import com.calblay.catering_api.utensils.application.usecase.GetAllUtensilsUseCase;
import com.calblay.catering_api.utensils.application.usecase.UpdateUtensilQuantityUseCase;
import com.calblay.catering_api.utensils.domain.model.Utensil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utensils")
public class UtensilController {

    private final CreateUtensilUseCase createUtensilUseCase;
    private final DeleteUtensilUseCase deleteUtensilUseCase;
    private final UpdateUtensilQuantityUseCase updateUtensilQuantityUseCase;
    private final GetAllUtensilsUseCase getAllUtensilsUseCase;

    public UtensilController(CreateUtensilUseCase createUtensilUseCase,
                             DeleteUtensilUseCase deleteUtensilUseCase,
                             UpdateUtensilQuantityUseCase updateUtensilQuantityUseCase,
                             GetAllUtensilsUseCase getAllUtensilsUseCase) {
        this.createUtensilUseCase = createUtensilUseCase;
        this.deleteUtensilUseCase = deleteUtensilUseCase;
        this.updateUtensilQuantityUseCase = updateUtensilQuantityUseCase;
        this.getAllUtensilsUseCase = getAllUtensilsUseCase;
    }

    // 1. Añadir nuevo utensilio
    //Se utiliza de la siguiente forma:
    //      /api/utensils
    @PostMapping
    public ResponseEntity<Utensil> createUtensil(@RequestBody CreateUtensilRequest request) {
        Utensil utensil = createUtensilUseCase.execute(
            request.getName(), 
            request.getCapacity(), 
            request.getQuantity()
        );
        return new ResponseEntity<>(utensil, HttpStatus.CREATED);
    }

    // 2. Ver todos los utensilios (Ver nombre, capacidad y cantidad)
    //Se utiliza de la siguiente forma:
    //      /api/utensils
    @GetMapping
    public ResponseEntity<List<Utensil>> getAllUtensils() {
        List<Utensil> utensils = getAllUtensilsUseCase.execute();
        return ResponseEntity.ok(utensils);
    }

    // 3. Editar cantidad de un utensilio
    //Se utiliza de la siguiente forma:
    //      /api/utensils/{idUtensil}/quantity
    @PutMapping("/{idUtensil}/quantity")
    public ResponseEntity<Utensil> updateQuantity(
            @PathVariable Integer idUtensil, 
            @RequestBody UpdateUtensilQuantityRequest request) {
        Utensil utensil = updateUtensilQuantityUseCase.execute(idUtensil, request.getQuantity());
        return ResponseEntity.ok(utensil);
    }

    // 4. Eliminar utensilio
    //Se utiliza de la siguiente forma:
    //      /api/utensils/{idUtensil}
    @DeleteMapping("/{idUtensil}")
    public ResponseEntity<Void> deleteUtensil(@PathVariable Integer idUtensil) {
        deleteUtensilUseCase.execute(idUtensil);
        return ResponseEntity.noContent().build();
    }
}
