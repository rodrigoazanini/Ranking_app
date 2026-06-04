package org.ranking_app.controller.category;

import org.ranking_app.dto.response.category.CategoryResponse;
import org.ranking_app.model.category.Category;
import org.ranking_app.service.category.CategoriesSearcherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categories")
public class CategoriesGetController {
    private final CategoriesSearcherService categoriesSearcherService;

    public CategoriesGetController(
            CategoriesSearcherService categoriesSearcherService
    ) {
        this.categoriesSearcherService = categoriesSearcherService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categories = categoriesSearcherService.findAll(pageable);

        return ResponseEntity.ok(
                categories.map(
                        category -> CategoryResponse.fromEntity(category)
                )
        );
    }
}
