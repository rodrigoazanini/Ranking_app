package org.ranking_app.service.item;

import org.ranking_app.exception.item.ItemNotFoundException;
import org.ranking_app.model.item.Item;
import org.ranking_app.repository.item.JpaItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemFinderService {

    private final JpaItemRepository jpaItemRepository;

    public ItemFinderService(JpaItemRepository jpaItemRepository) {
        this.jpaItemRepository = jpaItemRepository;
    }

    public Item find(Long id) {
        return jpaItemRepository.findById(id)
                .orElseThrow( () -> new ItemNotFoundException(id));
    }
}
