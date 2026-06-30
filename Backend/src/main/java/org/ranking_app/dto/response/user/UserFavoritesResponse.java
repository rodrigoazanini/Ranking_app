package org.ranking_app.dto.response.user;

import org.ranking_app.dto.response.user_item_favorite.FavoriteSummaryResponse;
import org.ranking_app.model.user.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserFavoritesResponse extends UserResponse {
    private List<FavoriteSummaryResponse> favorites;

    public UserFavoritesResponse() {}

    public UserFavoritesResponse(
        Long id,
        String username,
        String email,
        Boolean admin,
        List<FavoriteSummaryResponse> favorites
    ) {
        super(id, username, email, admin);
        this.favorites = favorites;
    }

    static public UserFavoritesResponse fromEntity(User user) {
        List<FavoriteSummaryResponse> favorites = user.getFavorites() != null
            ? user.getFavorites().stream()
                .map(FavoriteSummaryResponse::fromEntity)
                .collect(Collectors.toList())
            : List.of();

        return new UserFavoritesResponse(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getAdmin(),
            favorites
        );
    }

    public List<FavoriteSummaryResponse> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoriteSummaryResponse> favorites) {
        this.favorites = favorites;
    }
}
