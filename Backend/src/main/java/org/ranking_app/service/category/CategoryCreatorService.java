package org.ranking_app.service.category;

import org.ranking_app.dto.request.category.CategoryRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.repository.category.JpaCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryCreatorService {
    private final JpaCategoryRepository jpaCategoryRepository;

    public CategoryCreatorService(
        JpaCategoryRepository jpaCategoryRepository
    ) {
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    public Category create(CategoryRequest request) {
        Category category = Category.fromRequest(request);
        return jpaCategoryRepository.save(category);
    }
}
