package org.ranking_app.controller.review;

import org.ranking_app.dto.response.review.ReviewResponse;
import org.ranking_app.service.review.ReviewsGetByItemFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reviews")
public class ReviewsGetByItemController {
    private final ReviewsGetByItemFinderService reviewsGetByItemFinderService;

    public ReviewsGetByItemController(ReviewsGetByItemFinderService reviewsGetByItemFinderService) {
        this.reviewsGetByItemFinderService = reviewsGetByItemFinderService;
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByItem(@PathVariable Long itemId) {
        List<ReviewResponse> response = reviewsGetByItemFinderService.findReviewsByItemId(itemId)
                .stream()
                .map(ReviewResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }
}
