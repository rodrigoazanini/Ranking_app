package org.ranking_app.service.item;

import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;
import org.ranking_app.service.category.CategoryFinderService;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.service.user.UserFinderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemCreatorService {
    private final JpaItemRepository jpaItemRepository;
    private final CategoryFinderService categoryFinderService;
    private final UserFinderService userFinderService;

    public ItemCreatorService(
            JpaItemRepository jpaItemRepository,
            CategoryFinderService categoryFinderService,
            UserFinderService userFinderService
    ) {
        this.jpaItemRepository = jpaItemRepository;
        this.categoryFinderService = categoryFinderService;
        this.userFinderService = userFinderService;
    }

    @Transactional
    public Item create(ItemRequest request) {
        Category category = request.getCategoryId() != null ?
                categoryFinderService.find(request.getCategoryId()) : null;

        User suggested_by = request.getUserId() != null
                ? userFinderService.find(request.getUserId()) : null;

        Item item = Item.fromRequest(request);
        item.setCategory(category);
        item.setSuggested_by(suggested_by);

        return jpaItemRepository.save(item);
    }
}
