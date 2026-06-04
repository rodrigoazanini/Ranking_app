package org.ranking_app.controller.category;

import org.ranking_app.service.category.CategoryDeleterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryDeleteController {
    private final CategoryDeleterService categoryDeleterService;

    public CategoryDeleteController(CategoryDeleterService categoryDeleterService) {
        this.categoryDeleterService = categoryDeleterService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryDeleterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
