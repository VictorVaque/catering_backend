package com.calblay.catering_api.menus.adapters.outbound.persistence;

import com.calblay.catering_api.config.ImageStorageService;
import com.calblay.catering_api.menus.application.port.out.MenuImageStoragePort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class MenuImageStorageAdapter implements MenuImageStoragePort{
      private final ImageStorageService imageStorageService;

    public MenuImageStorageAdapter(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @Override
    public String uploadMenuImage(MultipartFile file) {
        return imageStorageService.store(file);
    }
}
