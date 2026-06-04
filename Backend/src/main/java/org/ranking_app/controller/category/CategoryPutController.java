package org.ranking_app.controller.category;

import jakarta.validation.Valid;
import org.ranking_app.dto.request.category.CategoryRequest;
import org.ranking_app.dto.response.category.CategoryResponse;
import org.ranking_app.model.category.Category;
import org.ranking_app.service.category.CategoryUpdaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/categories")
public class CategoryPutController {
    private final CategoryUpdaterService categoryUpdaterService;

    public CategoryPutController(
        CategoryUpdaterService categoryUpdaterService
    ) {
        this.categoryUpdaterService = categoryUpdaterService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        Category category = categoryUpdaterService.update(request, id);

        CategoryResponse response = CategoryResponse.fromEntity(category);

        return ResponseEntity.ok(response);
    }
}
