package org.ranking_app.service.item;

import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;
import org.ranking_app.service.category.CategoryFinderService;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.service.user.UserFinderService;
import jakarta.servlet.http.HttpServletRequest;
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


    // Método principal para crear un ítem, que extrae el usuario autenticado del token JWT
    @Transactional
    public Item create(ItemRequest request, HttpServletRequest httpRequest) {
        User suggestedBy = userFinderService.findAuthenticatedUser(httpRequest);
        return create(request, suggestedBy);
    }

    // Método privado para crear un ítem con un usuario sugerente específico (útil para pruebas o casos especiales como el seeder)
    @Transactional
    public Item create(ItemRequest request, User suggestedBy) {
        Category category = request.getCategoryId() != null ?
                categoryFinderService.find(request.getCategoryId()) : null;

        Item item = Item.fromRequest(request);
        if (!suggestedBy.getAdmin()) {
            item.setEnabled(false); // los ítems sugeridos por usuarios no admin deben estar deshabilitados por defecto
            item.setSuggested(true);//
        } else {
            item.setSuggested(false);
        }
        item.setCategory(category);
        item.setSuggested_by(suggestedBy);

        return jpaItemRepository.save(item);
    }
}
