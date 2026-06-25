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

@RestController
@RequestMapping("api/images")
public class UploadImagePostController {
    private final UploadImageService uploadImageService;

    public UploadImagePostController(UploadImageService uploadImageService) {
        this.uploadImageService = uploadImageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "File is empty"));
        }

        String fileName = uploadImageService.upload(file);
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/uploads/")
                .path(fileName)
                .toUriString();

        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
    }
}
