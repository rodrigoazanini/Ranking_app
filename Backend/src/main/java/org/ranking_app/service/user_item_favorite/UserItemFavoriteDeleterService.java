package org.ranking_app.service.user_item_favorite;

import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.ranking_app.repository.user_item_favorite.JpaUserItemFavoriteRepository;
import org.springframework.stereotype.Service;

@Service
public class UserItemFavoriteDeleterService {
    private final JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository;
    private final UserItemFavoriteFinderService userItemFavoriteFinderService;

    public UserItemFavoriteDeleterService(
        JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository,
        UserItemFavoriteFinderService userItemFavoriteFinderService
    ) {
        this.jpaUserItemFavoriteRepository = jpaUserItemFavoriteRepository;
        this.userItemFavoriteFinderService = userItemFavoriteFinderService;
    }

    public void delete(Long id) {
        UserItemFavorite userItemFavorite = userItemFavoriteFinderService.find(id);
        jpaUserItemFavoriteRepository.delete(userItemFavorite);
    }
}
