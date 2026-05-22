package com.calblay.catering_api.dishes.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.calblay.catering_api.dishes.application.port.out.DishImageStoragePort;
import com.calblay.catering_api.dishes.application.port.out.DishRepositoryPort;
import com.calblay.catering_api.dishes.adapters.inbound.rest.CreateDishRequest;

@Service
public class CreateDishUseCase {

    private final DishRepositoryPort dishRepositoryPort;
    private final DishImageStoragePort dishImageStoragePort;

    public CreateDishUseCase(DishRepositoryPort dishRepositoryPort, DishImageStoragePort dishImageStoragePort) {
        this.dishRepositoryPort = dishRepositoryPort;
        this.dishImageStoragePort = dishImageStoragePort;
    }

    public CreateDishResult createDish(CreateDishRequest request, MultipartFile image) {

        // el DTO se podría ocupar de estas validaciones. TO DO...
        // VALIDACIONES
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del plato es obligatorio");
        }

        if (request.getIngredientes() == null || request.getIngredientes().isEmpty()) {
            throw new RuntimeException("Debe existir al menos un ingrediente");
        }

        if (request.getPasosPreparacion() == null || request.getPasosPreparacion().isEmpty()) {
            throw new RuntimeException("Debe existir al menos un paso de preparación");
        }

        if (image != null && !image.isEmpty()) {
            String fileName = dishImageStoragePort.uploadDishImage(image);
            request.setImagenUrl(fileName);
        }
        // DELEGAR AL REPOSITORY
        return dishRepositoryPort.createDish(request);
    }
}
