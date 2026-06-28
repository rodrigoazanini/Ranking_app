package org.ranking_app.service.review;

import org.ranking_app.model.review.Review;
import org.ranking_app.repository.review.JpaReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsGetByUserFinderService {
    private final JpaReviewRepository jpaReviewRepository;

    public ReviewsGetByUserFinderService(JpaReviewRepository jpaReviewRepository) {
        this.jpaReviewRepository = jpaReviewRepository;
    }

    public List<Review> findReviewsByUserId(Long userId) {
        return jpaReviewRepository.findReviewsByUserId(userId);
    }
}
