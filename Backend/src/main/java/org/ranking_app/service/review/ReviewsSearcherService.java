package org.ranking_app.service.review;

import org.ranking_app.model.review.Review;
import org.ranking_app.repository.review.JpaReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewsSearcherService {
    private final JpaReviewRepository jpaReviewRepository;

    public ReviewsSearcherService(
        JpaReviewRepository jpaReviewRepository
    ) {
        this.jpaReviewRepository = jpaReviewRepository;
    }

    public Page<Review> findAll(Pageable pageable) {
        return jpaReviewRepository.findAll(pageable);
    }
}
