package org.ranking_app.service.user_item_favorite;

import org.ranking_app.dto.response.user_item_favorite.FavoriteCheckResponse;
import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.ranking_app.repository.user_item_favorite.JpaUserItemFavoriteRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FavoriteCheckerService {

    private final JpaUserItemFavoriteRepository favoriteRepository;

    public FavoriteCheckerService(JpaUserItemFavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public boolean isFavorited(Long userId, Long itemId) {
        return favoriteRepository.existsByUserIdAndItemId(userId, itemId);
    }

    public FavoriteCheckResponse checkFavorite(Long userId, Long itemId) {
        Optional<UserItemFavorite> favorite = favoriteRepository.findByUserIdAndItemId(userId, itemId);
        boolean isFavorited = favorite.isPresent();
        Long favoriteId = favorite.map(UserItemFavorite::getId).orElse(null);
        return new FavoriteCheckResponse(isFavorited, favoriteId);
    }
}
