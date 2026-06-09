package org.ranking_app.service.review;

import org.ranking_app.model.review.Review;
import org.ranking_app.repository.review.JpaReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewDeleterService {
    private final JpaReviewRepository jpaReviewRepository;
    private final ReviewFinderService reviewFinderService;

    public ReviewDeleterService(
        JpaReviewRepository jpaReviewRepository,
        ReviewFinderService reviewFinderService
    ) {
        this.jpaReviewRepository = jpaReviewRepository;
        this.reviewFinderService = reviewFinderService;
    }

    public void delete(Long id) {
        Review review = reviewFinderService.find(id);
        jpaReviewRepository.delete(review);
    }
}
