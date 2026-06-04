package org.ranking_app.service.item;

import org.ranking_app.model.item.Item;
import org.ranking_app.repository.item.JpaItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemsSearcherService {
    private final JpaItemRepository jpaItemRepository;

    public ItemsSearcherService(
            JpaItemRepository jpaItemRepository
    ) {
        this.jpaItemRepository = jpaItemRepository;
    }

    public Page<Item> findAll(Pageable pageable) {
        return jpaItemRepository.findAll(pageable);
    }
}
