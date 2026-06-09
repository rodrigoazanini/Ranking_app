package org.ranking_app.service.review;

import org.ranking_app.exception.review.ReviewNotFoundException;
import org.ranking_app.model.review.Review;
import org.ranking_app.repository.review.JpaReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewFinderService {

    private final JpaReviewRepository jpaReviewRepository;

    public ReviewFinderService(JpaReviewRepository jpaReviewRepository) {
        this.jpaReviewRepository = jpaReviewRepository;
    }

    public Review find(Long id) {
        return jpaReviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }
}
