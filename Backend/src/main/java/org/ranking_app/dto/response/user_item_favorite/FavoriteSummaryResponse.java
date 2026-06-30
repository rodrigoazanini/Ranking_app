package org.ranking_app.dto.response.user_item_favorite;

import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.model.user_item_favorite.UserItemFavorite;

public class FavoriteSummaryResponse {
    private Long id;
    private ItemResponse item;

    public FavoriteSummaryResponse() {}

    public FavoriteSummaryResponse(Long id, ItemResponse item) {
        this.id = id;
        this.item = item;
    }

    static public FavoriteSummaryResponse fromEntity(UserItemFavorite userItemFavorite) {
        return new FavoriteSummaryResponse(
            userItemFavorite.getId(),
            ItemResponse.fromEntity(userItemFavorite.getItem())
        );
    }

    public Long getId() {
        return id;
    }

    public ItemResponse getItem() {
        return item;
    }
}
