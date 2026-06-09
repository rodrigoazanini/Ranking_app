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
public class UserItemFavoriteUpdaterService {
    private final JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository;
    private final UserItemFavoriteFinderService userItemFavoriteFinderService;
    private final ItemFinderService itemFinderService;
    private final UserFinderService userFinderService;

    public UserItemFavoriteUpdaterService(
        JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository,
        UserItemFavoriteFinderService userItemFavoriteFinderService,
        ItemFinderService itemFinderService,
        UserFinderService userFinderService
    ) {
        this.jpaUserItemFavoriteRepository = jpaUserItemFavoriteRepository;
        this.userItemFavoriteFinderService = userItemFavoriteFinderService;
        this.itemFinderService = itemFinderService;
        this.userFinderService = userFinderService;
    }

    @Transactional
    public UserItemFavorite update(UserItemFavoriteRequest request, Long id) {
        UserItemFavorite userItemFavorite = userItemFavoriteFinderService.find(id);

        Item item = itemFinderService.find(request.getItemId());
        User user = userFinderService.find(request.getUserId());

        userItemFavorite.setItem(item);
        userItemFavorite.setUser(user);

        return jpaUserItemFavoriteRepository.save(userItemFavorite);
    }
}
