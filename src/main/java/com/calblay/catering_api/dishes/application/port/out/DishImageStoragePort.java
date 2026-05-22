package com.calblay.catering_api.dishes.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface DishImageStoragePort {
    String uploadDishImage(MultipartFile file);
}
