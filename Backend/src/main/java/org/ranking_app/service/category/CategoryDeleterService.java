package org.ranking_app.service.category;

import org.ranking_app.model.category.Category;
import org.ranking_app.repository.category.JpaCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryDeleterService {
    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryFinderService categoryFinderService;

    public CategoryDeleterService(
        JpaCategoryRepository jpaCategoryRepository,
        CategoryFinderService categoryFinderService
    ) {
        this.jpaCategoryRepository = jpaCategoryRepository;
        this.categoryFinderService = categoryFinderService;
    }

    public void delete(Long id) {
        Category category = categoryFinderService.find(id);
        jpaCategoryRepository.delete(category);
    }
}
