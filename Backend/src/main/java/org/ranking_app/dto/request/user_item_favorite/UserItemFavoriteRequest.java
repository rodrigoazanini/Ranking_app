package org.ranking_app.dto.request.user_item_favorite;

import jakarta.validation.constraints.NotNull;

public class UserItemFavoriteRequest {

    @NotNull(message = "El item es obligatorio")
    private Long itemId;

    @NotNull(message = "El usuario es obligatorio")
    private Long userId;

    public UserItemFavoriteRequest() {}

    public UserItemFavoriteRequest(
        Long itemId,
        Long userId
    ) {
        this.itemId = itemId;
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getUserId() {
        return userId;
    }
}
