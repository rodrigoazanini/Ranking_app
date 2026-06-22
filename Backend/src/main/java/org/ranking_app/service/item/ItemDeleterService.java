package org.ranking_app.service.item;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.service.user.UserFinderService;
import org.springframework.stereotype.Service;

@Service
public class ItemDeleterService {
    private final JpaItemRepository jpaItemRepository;
    private final ItemFinderService itemFinderService;
    private final UserFinderService userFinderService;

    public ItemDeleterService(
            JpaItemRepository jpaItemRepository,
            ItemFinderService itemFinderService,
            UserFinderService userFinderService
    ) {
        this.jpaItemRepository = jpaItemRepository;
        this.itemFinderService = itemFinderService;
        this.userFinderService = userFinderService;
    }

    public void delete(Long id, HttpServletRequest httpRequest) {
        Item item = itemFinderService.find(id);

        User requester = userFinderService.findAuthenticatedUser(httpRequest);

        // se permite eliminar si el requester es admin
        if (requester.getAdmin()) {
            jpaItemRepository.delete(item);
            return;
        }

        // se permite eliminar si el requester es el que sugirió el item y el item no está marcado como sugerido
        if (item.getSuggested_by() != null && 
        item.getSuggested_by().getId().equals(requester.getId()) &&
         Boolean.FALSE.equals(item.getSuggested())) {
            jpaItemRepository.delete(item);
            return;
        }

        throw new SecurityException("Acceso denegado");
    }
}
