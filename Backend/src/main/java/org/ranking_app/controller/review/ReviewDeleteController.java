package org.ranking_app.controller.review;

import org.ranking_app.service.review.ReviewDeleterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reviews")
public class ReviewDeleteController {
    private final ReviewDeleterService reviewDeleterService;

    public ReviewDeleteController(ReviewDeleterService reviewDeleterService) {
        this.reviewDeleterService = reviewDeleterService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewDeleterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
