package org.ranking_app.service.item;

import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.service.category.CategoryFinderService;
import org.ranking_app.service.user.UserFinderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cache.annotation.CacheEvict;
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
            UserFinderService userFinderService) {
        this.jpaItemRepository = jpaItemRepository;
        this.itemFinderService = itemFinderService;
        this.categoryFinderService = categoryFinderService;
        this.userFinderService = userFinderService;
    }

    @CacheEvict(value = {"searchCache", "navBarSearchCache"}, allEntries = true)
    @Transactional
    public Item update(ItemRequest itemRequest, Long id, HttpServletRequest httpRequest) {
        Item item = itemFinderService.find(id);

        if (itemRequest.getName() != null) {
            item.setName(itemRequest.getName());
        }
        if (itemRequest.getDescription() != null) {
            item.setDescription(itemRequest.getDescription());
        }
        if (itemRequest.getBrand() != null) {
            item.setBrand(itemRequest.getBrand());
        }
        if (itemRequest.getWeight() != null) {
            item.setWeigth(itemRequest.getWeight());
        }
        if (itemRequest.getEnabled() != null) {
            item.setEnabled(itemRequest.getEnabled());
        }
        // Si el usuario autenticado es admin, se actualiza el campo suggested a false
        User authenticatedUser = userFinderService.findAuthenticatedUser(httpRequest);

        if (authenticatedUser != null && authenticatedUser.getAdmin()) {
            item.setSuggested(false);
        } else {
            if (itemRequest.getSuggested() != null) {
                item.setSuggested(itemRequest.getSuggested());
            }
        }
        if (itemRequest.getCategoryId() != null) {
            Category category = categoryFinderService.find(itemRequest.getCategoryId());
            item.setCategory(category);
        }
        // No se permite actualizar el campo suggested_by,
        // ya que solo se asigna al crear el item y no debería cambiarse después.

        return jpaItemRepository.save(item);
    }
}
