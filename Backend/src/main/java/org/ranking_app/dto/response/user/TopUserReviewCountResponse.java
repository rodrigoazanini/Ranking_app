package org.ranking_app.dto.response.user;

import org.ranking_app.repository.user.TopUserReviewCountProjection;

public class TopUserReviewCountResponse {
    private String username;
    private Long reviewCount;

    public TopUserReviewCountResponse() {}

    public TopUserReviewCountResponse(String username, Long reviewCount) {
        this.username = username;
        this.reviewCount = reviewCount;
    }

    static public TopUserReviewCountResponse fromProjection(TopUserReviewCountProjection projection) {
        return new TopUserReviewCountResponse(
            projection.getUserName(),
            projection.getReviewCount()
        );
    }

    public String getUsername() {
        return username;
    }

    public Long getReviewCount() {
        return reviewCount;
    }
}
