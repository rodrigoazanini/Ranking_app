package org.ranking_app.dto.response.user_item_favorite;

import org.ranking_app.model.user_item_favorite.UserItemFavorite;

public class UserItemFavoriteResponse {
    private Long id;
    private Long itemId;
    private Long userId;

    public UserItemFavoriteResponse() {}

    public UserItemFavoriteResponse(
        Long id,
        Long itemId,
        Long userId
    ) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
    }

    static public UserItemFavoriteResponse fromEntity(UserItemFavorite userItemFavorite) {
        return new UserItemFavoriteResponse(
            userItemFavorite.getId(),
            userItemFavorite.getItem() != null ? userItemFavorite.getItem().getId() : null,
            userItemFavorite.getUser() != null ? userItemFavorite.getUser().getId() : null
        );
    }

    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getUserId() {
        return userId;
    }
}
