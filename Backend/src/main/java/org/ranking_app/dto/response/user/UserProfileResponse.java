package org.ranking_app.dto.response.user;

import org.ranking_app.dto.response.review.ReviewItemSummaryResponse;
import org.ranking_app.dto.response.user_item_favorite.FavoriteSummaryResponse;
import org.ranking_app.model.user.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserProfileResponse {
    private List<FavoriteSummaryResponse> favorites;
    private List<ReviewItemSummaryResponse> reviews;

    public UserProfileResponse() {}

    public UserProfileResponse(
        List<FavoriteSummaryResponse> favorites,
        List<ReviewItemSummaryResponse> reviews
    ) {
        this.favorites = favorites;
        this.reviews = reviews;
    }

    static public UserProfileResponse fromEntity(User user) {
        List<FavoriteSummaryResponse> favorites = user.getFavorites() != null
            ? user.getFavorites().stream()
                .map(FavoriteSummaryResponse::fromEntity)
                .collect(Collectors.toList())
            : List.of();

        List<ReviewItemSummaryResponse> reviews = user.getReviews() != null
            ? user.getReviews().stream()
                .map(ReviewItemSummaryResponse::fromEntity)
                .collect(Collectors.toList())
            : List.of();

        return new UserProfileResponse(favorites, reviews);
    }

    public List<FavoriteSummaryResponse> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoriteSummaryResponse> favorites) {
        this.favorites = favorites;
    }

    public List<ReviewItemSummaryResponse> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewItemSummaryResponse> reviews) {
        this.reviews = reviews;
    }
}
