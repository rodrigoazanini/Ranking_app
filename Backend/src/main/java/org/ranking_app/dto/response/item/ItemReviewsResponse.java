package org.ranking_app.dto.response.item;

import org.ranking_app.dto.response.review.ReviewSummaryResponse;
import org.ranking_app.dto.response.category.CategoryResponse;
import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.item.Item;

import java.util.List;

public class ItemReviewsResponse extends ItemResponse {
    private List<ReviewSummaryResponse> reviews;

    public ItemReviewsResponse() {}

    public ItemReviewsResponse(
        Long id,
        String name,
        String description,
        String brand,
        Double weight,
        Double priceMin,
        Double priceMax,
        Double rankingAvg,
        Boolean enabled,
        Boolean suggested,
        String imageUrl,
        CategoryResponse categoryResponse,
        UserResponse userResponse,
        List<ReviewSummaryResponse> reviews
    ) {
        super(id, name, imageUrl, description, brand, weight, priceMin, priceMax, rankingAvg, enabled, suggested, categoryResponse, userResponse);
        this.reviews = reviews;
    }    

    static public ItemReviewsResponse fromEntity(Item item) {
        List<ReviewSummaryResponse> reviews = item.getReviews() == null
            ? List.of()
            : item.getReviews().stream()
            .map(ReviewSummaryResponse::fromEntity).toList();
        return new ItemReviewsResponse(
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.getBrand(),
            item.getWeigth(),
            item.getPriceMin(),
            item.getPriceMax(),
            item.getRankingAvg(),
            item.getEnabled(),
            item.getSuggested(),
            item.getImageUrl(),
            item.getCategory() != null ? CategoryResponse.fromEntity(item.getCategory()) : null,
            item.getSuggested_by() != null ? UserResponse.fromEntity(item.getSuggested_by()) : null,
            reviews
        );
    }

    public List<ReviewSummaryResponse> getReviews() {
        return reviews;
    }
}