package org.ranking_app.service.category;

import org.ranking_app.model.category.Category;
import org.ranking_app.repository.category.JpaCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoriesSearcherService {
    private final JpaCategoryRepository jpaCategoryRepository;

    public CategoriesSearcherService(
        JpaCategoryRepository jpaCategoryRepository
    ) {
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    public Page<Category> findAll(Pageable pageable) {
        return jpaCategoryRepository.findAll(pageable);
    }
}
