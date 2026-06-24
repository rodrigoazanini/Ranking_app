package org.ranking_app.controller.upload_image;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.service.uploadImage.UploadImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("api/images")
public class UploadImagePostController {
    private final UploadImageService uploadImageService;

    public UploadImagePostController(UploadImageService uploadImageService) {
        this.uploadImageService = uploadImageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        String fileName = uploadImageService.upload(file);

        return ResponseEntity.ok(fileName);
    }
}
