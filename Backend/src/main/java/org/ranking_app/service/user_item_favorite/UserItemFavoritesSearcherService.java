package org.ranking_app.service.user_item_favorite;

import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.ranking_app.repository.user_item_favorite.JpaUserItemFavoriteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserItemFavoritesSearcherService {
    private final JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository;

    public UserItemFavoritesSearcherService(
        JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository
    ) {
        this.jpaUserItemFavoriteRepository = jpaUserItemFavoriteRepository;
    }

    public Page<UserItemFavorite> findAll(Pageable pageable) {
        return jpaUserItemFavoriteRepository.findAll(pageable);
    }
}
