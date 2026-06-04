package org.ranking_app.service.category;

import org.ranking_app.dto.request.category.CategoryRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.repository.category.JpaCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryUpdaterService {
    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryFinderService categoryFinderService;

    public CategoryUpdaterService(
            JpaCategoryRepository jpaCategoryRepository,
            CategoryFinderService categoryFinderService
    ) {
        this.jpaCategoryRepository = jpaCategoryRepository;
        this.categoryFinderService = categoryFinderService;
    }

    public Category update(CategoryRequest categoryRequest, Long id) {
        Category category = categoryFinderService.find(id);

        category.setName(categoryRequest.getName());

        return jpaCategoryRepository.save(category);
    }
}
