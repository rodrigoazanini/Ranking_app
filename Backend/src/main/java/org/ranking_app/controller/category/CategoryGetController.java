package org.ranking_app.controller.category;

import org.ranking_app.dto.response.category.CategoryResponse;
import org.ranking_app.model.category.Category;
import org.ranking_app.service.category.CategoryFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoryGetController {

    private final CategoryFinderService categoryFinderService;

    public CategoryGetController(CategoryFinderService categoryFinderService) {
        this.categoryFinderService = categoryFinderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> find(@PathVariable Long id) {
       Category category = categoryFinderService.find(id);

       CategoryResponse response = CategoryResponse.fromEntity(category);

       return ResponseEntity.ok(response);
    }
}
