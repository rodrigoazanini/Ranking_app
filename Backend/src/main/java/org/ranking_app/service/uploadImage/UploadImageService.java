package org.ranking_app.service.uploadImage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadImageService {

    public UploadImageService() {}

    public String upload(MultipartFile file) throws IOException {

        //TODO no devolver string sino crear un imageResponse(Filename)
        Path uploadPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "images", "uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 3. Generate a unique file name to avoid duplicates/overwriting
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : ".jpg";
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // 4. Resolve the path and save the file
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);

        return uniqueFileName;
    }
}
