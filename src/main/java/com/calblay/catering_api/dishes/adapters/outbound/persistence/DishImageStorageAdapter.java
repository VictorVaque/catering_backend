package com.calblay.catering_api.dishes.adapters.outbound.persistence;

import com.calblay.catering_api.config.ImageStorageService;
import com.calblay.catering_api.dishes.application.port.out.DishImageStoragePort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DishImageStorageAdapter implements DishImageStoragePort {

    private final ImageStorageService imageStorageService;

    public DishImageStorageAdapter(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @Override
    public String uploadDishImage(MultipartFile file) {
        return imageStorageService.store(file);
    }
}