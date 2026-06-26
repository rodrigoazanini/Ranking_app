package org.ranking_app.controller.upload_image;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.service.uploadImage.UploadImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/images")
public class UploadImagePostController {
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("png", "jpg", "jpeg", "webp", "avif");
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/png", "image/jpg", "image/jpeg", "image/webp", "image/avif");

    private final UploadImageService uploadImageService;

    public UploadImagePostController(UploadImageService uploadImageService) {
        this.uploadImageService = uploadImageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "File is empty"));
        }

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase() : "";
        String contentType = file.getContentType() != null ? file.getContentType().toLowerCase() : "";

        if (!ALLOWED_EXTENSIONS.contains(extension) || (!contentType.isBlank() && !ALLOWED_CONTENT_TYPES.contains(contentType))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Unsupported file type. Allowed types: png, jpg, jpeg, webp, avif"));
        }

        String fileName = uploadImageService.upload(file);
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/images/")
                .path(fileName)
                .toUriString();

        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
    }
}
