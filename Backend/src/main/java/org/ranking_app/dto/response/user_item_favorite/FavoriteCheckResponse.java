package org.ranking_app.dto.response.user_item_favorite;

public class FavoriteCheckResponse {
    private boolean isFavorited;
    private Long favoriteId;

    public FavoriteCheckResponse(boolean isFavorited, Long favoriteId) {
        this.isFavorited = isFavorited;
        this.favoriteId = favoriteId;
    }

    public boolean getIsFavorited() {
        return isFavorited;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }
}
