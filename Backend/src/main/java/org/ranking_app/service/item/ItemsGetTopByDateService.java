package org.ranking_app.service.item;

import org.ranking_app.model.item.Item;
import org.ranking_app.repository.item.JpaItemRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemsGetTopByDateService {
    private final JpaItemRepository jpaItemRepository;

    public ItemsGetTopByDateService(JpaItemRepository jpaItemRepository) {
        this.jpaItemRepository = jpaItemRepository;
    }

    public List<Item> findLatestItemsUploadedUpTo(Date date, int quantity) {
        int safeQuantity = Math.max(1, quantity);
        return jpaItemRepository.findLatestItemsUploadedUpTo(date, PageRequest.of(0, safeQuantity));
    }
}
