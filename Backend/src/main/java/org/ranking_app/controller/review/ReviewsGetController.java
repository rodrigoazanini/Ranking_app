package org.ranking_app.controller.review;

import org.ranking_app.dto.response.review.ReviewResponse;
import org.ranking_app.model.review.Review;
import org.ranking_app.service.review.ReviewsSearcherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reviews")
public class ReviewsGetController {
    private final ReviewsSearcherService reviewsSearcherService;

    public ReviewsGetController(
        ReviewsSearcherService reviewsSearcherService
    ) {
        this.reviewsSearcherService = reviewsSearcherService;
    }

    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> search(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewsSearcherService.findAll(pageable);

        return ResponseEntity.ok(
            reviews.map(ReviewResponse::fromEntity)
        );
    }
}
