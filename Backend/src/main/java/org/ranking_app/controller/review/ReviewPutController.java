package org.ranking_app.controller.review;

import jakarta.validation.Valid;
import org.ranking_app.dto.request.review.ReviewRequest;
import org.ranking_app.dto.response.review.ReviewResponse;
import org.ranking_app.model.review.Review;
import org.ranking_app.service.review.ReviewUpdaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reviews")
public class ReviewPutController {
    private final ReviewUpdaterService reviewUpdaterService;

    public ReviewPutController(
        ReviewUpdaterService reviewUpdaterService
    ) {
        this.reviewUpdaterService = reviewUpdaterService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody ReviewRequest request
    ) {
        Review review = reviewUpdaterService.update(request, id);

        ReviewResponse response = ReviewResponse.fromEntity(review);

        return ResponseEntity.ok(response);
    }
}
