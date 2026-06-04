package org.ranking_app.service.item;

import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.model.item.Item;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.service.category.CategoryFinderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemUpdaterService {
    private final JpaItemRepository jpaItemRepository;
    private final ItemFinderService itemFinderService;
    private final CategoryFinderService categoryFinderService;

    public ItemUpdaterService(
            JpaItemRepository jpaItemRepository,
            ItemFinderService itemFinderService,
            CategoryFinderService categoryFinderService
    ) {
        this.jpaItemRepository = jpaItemRepository;
        this.itemFinderService = itemFinderService;
        this.categoryFinderService = categoryFinderService;
    }

    @Transactional
    public Item update(ItemRequest itemRequest, Long id) {
        Item item = itemFinderService.find(id);
        Category category = categoryFinderService.find(itemRequest.getCategoryId());

        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setPrice(itemRequest.getPrice());
        item.setActive(itemRequest.getActive());
        item.setCategory(category);

        return jpaItemRepository.save(item);
    }
}
