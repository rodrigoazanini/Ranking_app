package org.ranking_app.service.item;

import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.model.item.Item;
import org.ranking_app.service.category.CategoryFinderService;
import org.ranking_app.repository.item.JpaItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemCreatorService {
    private final JpaItemRepository jpaItemRepository;
    private final CategoryFinderService categoryFinderService;

    public ItemCreatorService(
            JpaItemRepository jpaItemRepository,
            CategoryFinderService categoryFinderService
    ) {
        this.jpaItemRepository = jpaItemRepository;
        this.categoryFinderService = categoryFinderService;
    }

    @Transactional
    public Item create(ItemRequest request) {
        Category category = categoryFinderService.find(request.getCategoryId());
        Item item = Item.fromRequest(request, category);
        return jpaItemRepository.save(item);
    }
}
