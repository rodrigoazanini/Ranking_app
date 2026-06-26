package org.ranking_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = resolveUploadPath();
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations(uploadPath.toUri().toString());
    }

    private Path resolveUploadPath() {
        Path currentDir = Paths.get("").toAbsolutePath().normalize();

        Path backendRoot = currentDir.resolve("Backend");
        if (Files.exists(backendRoot.resolve("pom.xml")) && Files.exists(backendRoot.resolve("src"))) {
            return backendRoot.resolve("uploads").resolve("images").normalize();
        }

        if (Files.exists(currentDir.resolve("pom.xml")) && Files.exists(currentDir.resolve("src"))) {
            return currentDir.resolve("uploads").resolve("images").normalize();
        }

        return currentDir.resolve("uploads").resolve("images").normalize();
    }
}
