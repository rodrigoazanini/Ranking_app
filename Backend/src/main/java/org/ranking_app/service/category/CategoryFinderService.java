package org.ranking_app.service.category;

import org.ranking_app.exception.category.CategoryNotFoundException;
import org.ranking_app.model.category.Category;
import org.ranking_app.repository.category.JpaCategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryFinderService {

    private final JpaCategoryRepository jpaCategoryRepository;

    public CategoryFinderService(JpaCategoryRepository jpaCategoryRepository) {
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    public Category find(Long id) {
        return jpaCategoryRepository.findById(id)
                .orElseThrow( () -> new CategoryNotFoundException(id));
    }
}
