package org.ranking_app.service.user_item_favorite;

import org.ranking_app.exception.user_item_favorite.UserItemFavoriteNotFoundException;
import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.ranking_app.repository.user_item_favorite.JpaUserItemFavoriteRepository;
import org.springframework.stereotype.Service;

@Service
public class UserItemFavoriteFinderService {

    private final JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository;

    public UserItemFavoriteFinderService(
        JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository
    ) {
        this.jpaUserItemFavoriteRepository = jpaUserItemFavoriteRepository;
    }

    public UserItemFavorite find(Long id) {
        return jpaUserItemFavoriteRepository.findById(id)
            .orElseThrow(() -> new UserItemFavoriteNotFoundException(id));
    }
}
