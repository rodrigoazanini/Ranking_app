package org.ranking_app.controller.review;

import org.ranking_app.dto.response.review.ReviewResponse;
import org.ranking_app.service.review.ReviewsGetByUserFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reviews")
public class ReviewsGetByUserController {
    private final ReviewsGetByUserFinderService reviewsGetByUserFinderService;

    public ReviewsGetByUserController(ReviewsGetByUserFinderService reviewsGetByUserFinderService) {
        this.reviewsGetByUserFinderService = reviewsGetByUserFinderService;
    }

    @GetMapping("/u:{userId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUser(@PathVariable Long userId) {
        List<ReviewResponse> response = reviewsGetByUserFinderService.findReviewsByUserId(userId)
                .stream()
                .map(ReviewResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }
}
