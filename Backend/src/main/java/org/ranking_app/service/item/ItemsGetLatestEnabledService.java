package org.ranking_app.service.item;

import org.ranking_app.model.item.Item;
import org.ranking_app.repository.item.JpaItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemsGetLatestEnabledService {

    private final JpaItemRepository jpaItemRepository;

    public ItemsGetLatestEnabledService(JpaItemRepository jpaItemRepository) {
        this.jpaItemRepository = jpaItemRepository;
    }

    public Page<Item> findLatestEnabledItems(Pageable pageable) {
        return jpaItemRepository.findLatestEnabledItems(pageable);
    }
}
