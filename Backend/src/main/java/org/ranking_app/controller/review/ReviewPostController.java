package org.ranking_app.controller.review;

import jakarta.validation.Valid;
import org.ranking_app.dto.request.review.ReviewRequest;
import org.ranking_app.dto.response.review.ReviewResponse;
import org.ranking_app.model.review.Review;
import org.ranking_app.service.review.ReviewCreatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reviews")
public class ReviewPostController {
    private final ReviewCreatorService reviewCreatorService;

    public ReviewPostController(ReviewCreatorService reviewCreatorService) {
        this.reviewCreatorService = reviewCreatorService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> create(
        @Valid @RequestBody ReviewRequest reviewRequest
    ) {
        Review review = reviewCreatorService.create(reviewRequest);

        ReviewResponse reviewResponse = ReviewResponse.fromEntity(review);

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponse);
    }
}
