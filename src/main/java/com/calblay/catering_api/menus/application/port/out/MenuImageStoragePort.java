package com.calblay.catering_api.menus.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface MenuImageStoragePort {
    String uploadMenuImage(MultipartFile file);
}
