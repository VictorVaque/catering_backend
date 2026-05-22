package com.calblay.catering_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageStorageService {

    @Value("${storage.location}")
    private String storageLocation;

    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("No se puede guardar un archivo vacío.");
            }

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + "." + extension;

            Path rootLocation = Paths.get(storageLocation);
            // got errors uploading images via postman. Trying to see if the path is not
            // correct (should delete later, this is only for debug)
            Files.createDirectories(rootLocation);
            Path destinationFile = rootLocation.resolve(fileName)
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new RuntimeException("No se puede guardar fuera del directorio actual.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al fallar el almacenamiento en NFSasaa", e);
        }
    }
}
