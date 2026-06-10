package org.ranking_app.dto.response.user_item_favorite;

import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user_item_favorite.UserItemFavorite;

public class UserItemFavoriteResponse {
    private Long id;
    private ItemResponse itemResponse;
    private UserResponse userResponse;

    public UserItemFavoriteResponse() {}

    public UserItemFavoriteResponse(
        Long id,
        ItemResponse itemResponse,
        UserResponse userResponse
    ) {
        this.id = id;
        this.itemResponse = itemResponse;
        this.userResponse = userResponse;
    }

    static public UserItemFavoriteResponse fromEntity(UserItemFavorite userItemFavorite) {
        return new UserItemFavoriteResponse(
                userItemFavorite.getId(),
                ItemResponse.fromEntity(userItemFavorite.getItem()),
                UserResponse.fromEntity(userItemFavorite.getUser())
        );
    }

    public Long getId() {
        return id;
    }

    public ItemResponse getitemResponse() {
        return itemResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }
}
