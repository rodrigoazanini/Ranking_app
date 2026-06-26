package org.ranking_app.service.uploadImage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UploadImageService {

    private static final String UPLOAD_DIRECTORY = "uploads/images";
    private final Path uploadPath;

    public UploadImageService() {
        this.uploadPath = resolveUploadPath();
    }

    public String upload(MultipartFile file) throws IOException {
        Files.createDirectories(uploadPath);

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : ".jpg";
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    public Path getUploadPath() {
        return uploadPath;
    }

    private Path resolveUploadPath() {
        Path currentDir = Paths.get("").toAbsolutePath().normalize();

        Path backendRoot = currentDir.resolve("Backend");
        if (Files.exists(backendRoot.resolve("pom.xml")) && Files.exists(backendRoot.resolve("src"))) {
            return backendRoot.resolve(UPLOAD_DIRECTORY).normalize();
        }

        if (Files.exists(currentDir.resolve("pom.xml")) && Files.exists(currentDir.resolve("src"))) {
            return currentDir.resolve(UPLOAD_DIRECTORY).normalize();
        }

        return currentDir.resolve(UPLOAD_DIRECTORY).normalize();
    }
}
