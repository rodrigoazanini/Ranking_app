package org.ranking_app.controller.category;

import jakarta.validation.Valid;
import org.ranking_app.dto.request.category.CategoryRequest;
import org.ranking_app.dto.response.category.CategoryResponse;
import org.ranking_app.model.category.Category;
import org.ranking_app.service.category.CategoryCreatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryPostController {
    private final CategoryCreatorService categoryCreatorService;

    public CategoryPostController(CategoryCreatorService categoryCreatorService) {
        this.categoryCreatorService = categoryCreatorService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(
            @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        Category category =  categoryCreatorService.create(categoryRequest);

        CategoryResponse categoryResponse = CategoryResponse.fromEntity(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }
}
