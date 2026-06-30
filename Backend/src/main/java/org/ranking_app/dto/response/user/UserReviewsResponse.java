package org.ranking_app.dto.response.user;

import org.ranking_app.dto.response.review.ReviewItemSummaryResponse;
import org.ranking_app.model.user.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserReviewsResponse extends UserResponse {
    private List<ReviewItemSummaryResponse> reviews;

    public UserReviewsResponse() {}

    public UserReviewsResponse(
        Long id,
        String username,
        String email,
        Boolean admin,
        List<ReviewItemSummaryResponse> reviews
    ) {
        super(id, username, email, admin);
        this.reviews = reviews;
    }

    static public UserReviewsResponse fromEntity(User user) {
        List<ReviewItemSummaryResponse> reviews = user.getReviews() != null
            ? user.getReviews().stream()
                .map(ReviewItemSummaryResponse::fromEntity)
                .collect(Collectors.toList())
            : List.of();

        return new UserReviewsResponse(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getAdmin(),
            reviews
        );
    }

    public List<ReviewItemSummaryResponse> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewItemSummaryResponse> reviews) {
        this.reviews = reviews;
    }
}
