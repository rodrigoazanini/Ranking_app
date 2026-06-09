package org.ranking_app.service.item;

import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.service.category.CategoryFinderService;
import org.ranking_app.service.user.UserFinderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemUpdaterService {
    private final JpaItemRepository jpaItemRepository;
    private final ItemFinderService itemFinderService;
    private final CategoryFinderService categoryFinderService;
    private final UserFinderService userFinderService;

    public ItemUpdaterService(
            JpaItemRepository jpaItemRepository,
            ItemFinderService itemFinderService,
            CategoryFinderService categoryFinderService,
            UserFinderService userFinderService
    ) {
        this.jpaItemRepository = jpaItemRepository;
        this.itemFinderService = itemFinderService;
        this.categoryFinderService = categoryFinderService;
        this.userFinderService = userFinderService;
    }

    @Transactional
    public Item update(ItemRequest itemRequest, Long id) {
        Item item = itemFinderService.find(id);

        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setBrand(item.getBrand());
        item.setWeigth(item.getWeigth());
//        item.setPriceMin(itemRequest.getPriceMin());
//        item.setPriceMax(itemRequest.getPriceMax());
//        item.setRankingAvg(item.getRankingAvg());
        item.setEnabled(itemRequest.getEnabled());
        item.setSuggested(item.getSuggested());

        if (itemRequest.getCategoryId() != null){
            Category category = categoryFinderService.find(itemRequest.getCategoryId());
            item.setCategory(category);
        }

        if (itemRequest.getUserId() != null) {
            User user = userFinderService.find(itemRequest.getUserId());
            item.setSuggested_by(user);
        }

        return jpaItemRepository.save(item);
    }
}
