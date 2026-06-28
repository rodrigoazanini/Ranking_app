package org.ranking_app.service.item;

import org.ranking_app.model.item.Item;
import org.ranking_app.repository.item.JpaItemRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsGetTopByReviewsService {
    private final JpaItemRepository jpaItemRepository;

    public ItemsGetTopByReviewsService(JpaItemRepository jpaItemRepository) {
        this.jpaItemRepository = jpaItemRepository;
    }

    public List<Item> findTopItemsByReviewCount(int quantity) {
        return jpaItemRepository.findTopItemsByReviewCount(PageRequest.of(0, Math.max(1, quantity)));
    }
}
