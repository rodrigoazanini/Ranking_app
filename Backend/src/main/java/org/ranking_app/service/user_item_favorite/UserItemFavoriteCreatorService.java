package org.ranking_app.service.user_item_favorite;

import org.ranking_app.dto.request.user_item_favorite.UserItemFavoriteRequest;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;
import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.ranking_app.repository.user_item_favorite.JpaUserItemFavoriteRepository;
import org.ranking_app.service.item.ItemFinderService;
import org.ranking_app.service.user.UserFinderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserItemFavoriteCreatorService {
    private final JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository;
    private final ItemFinderService itemFinderService;
    private final UserFinderService userFinderService;

    public UserItemFavoriteCreatorService(
        JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository,
        ItemFinderService itemFinderService,
        UserFinderService userFinderService
    ) {
        this.jpaUserItemFavoriteRepository = jpaUserItemFavoriteRepository;
        this.itemFinderService = itemFinderService;
        this.userFinderService = userFinderService;
    }

    @Transactional
    public UserItemFavorite create(UserItemFavoriteRequest request) {
        Item item = itemFinderService.find(request.getItemId());
        User user = userFinderService.find(request.getUserId());

        UserItemFavorite userItemFavorite = UserItemFavorite.fromRequest(item, user);
        return jpaUserItemFavoriteRepository.save(userItemFavorite);
    }
}
