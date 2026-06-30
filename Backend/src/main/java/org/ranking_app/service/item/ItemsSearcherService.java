package org.ranking_app.service.item;

import org.ranking_app.dto.request.item.ItemSearchFilterRequest;
import org.ranking_app.dto.request.item.ItemSearchRequest;
import org.ranking_app.model.item.Item;
import org.ranking_app.repository.item.JpaItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<Item> search(ItemSearchRequest request, Pageable pageable) {
        String pattern = null;
        if (request.getQuery() != null && !request.getQuery().isBlank()) {
            pattern = "%" + request.getQuery() + "%";
        }
        if (request instanceof ItemSearchFilterRequest filter) {
            String brand = null;
            if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
                brand = "%" + filter.getBrand() + "%";
            }
            String category = null;
            if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
                category = "%" + filter.getCategory() + "%";
            }
            return jpaItemRepository.findByFilters(
                    pattern,
                    brand,
                    filter.getSuggested(),
                    filter.getEnabled(),
                    category,
                    pageable
            );
        }
        return jpaItemRepository.findByFilters(
                pattern,
                null,
                null,
                null,
                null,
                pageable
        );
    }
}
