package org.ranking_app.service.item;

import org.ranking_app.model.item.Item;
import org.ranking_app.repository.item.JpaItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemDeleterService {
    private final JpaItemRepository jpaItemRepository;
    private final ItemFinderService itemFinderService;

    public ItemDeleterService(
            JpaItemRepository jpaItemRepository,
            ItemFinderService itemFinderService
    ) {
        this.jpaItemRepository = jpaItemRepository;
        this.itemFinderService = itemFinderService;
    }

    public void delete(Long id) {
        Item item = itemFinderService.find(id);
        jpaItemRepository.delete(item);
    }
}
