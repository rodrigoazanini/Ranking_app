package org.ranking_app.controller.review;

import org.ranking_app.dto.response.review.ReviewResponse;
import org.ranking_app.model.review.Review;
import org.ranking_app.service.review.ReviewFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reviews")
public class ReviewGetController {

    private final ReviewFinderService reviewFinderService;

    public ReviewGetController(ReviewFinderService reviewFinderService) {
        this.reviewFinderService = reviewFinderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> find(@PathVariable Long id) {
        Review review = reviewFinderService.find(id);

        ReviewResponse response = ReviewResponse.fromEntity(review);

        return ResponseEntity.ok(response);
    }
}
