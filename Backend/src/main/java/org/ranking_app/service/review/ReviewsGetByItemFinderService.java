package org.ranking_app.service.review;

import org.ranking_app.model.review.Review;
import org.ranking_app.repository.review.JpaReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsGetByItemFinderService {
    private final JpaReviewRepository jpaReviewRepository;

    public ReviewsGetByItemFinderService(JpaReviewRepository jpaReviewRepository) {
        this.jpaReviewRepository = jpaReviewRepository;
    }

    public List<Review> findReviewsByItemId(Long itemId) {
        return jpaReviewRepository.findReviewsByItemId(itemId);
    }
}
