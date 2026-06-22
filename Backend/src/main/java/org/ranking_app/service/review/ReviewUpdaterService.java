package org.ranking_app.service.review;

import org.ranking_app.dto.request.review.ReviewRequest;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.review.Review;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.review.JpaReviewRepository;
import org.ranking_app.service.item.ItemFinderService;
import org.ranking_app.service.user.UserFinderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewUpdaterService {
    private final JpaReviewRepository jpaReviewRepository;
    private final ReviewFinderService reviewFinderService;
    private final ItemFinderService itemFinderService;
    private final UserFinderService userFinderService;

    public ReviewUpdaterService(
        JpaReviewRepository jpaReviewRepository,
        ReviewFinderService reviewFinderService,
        ItemFinderService itemFinderService,
        UserFinderService userFinderService
    ) {
        this.jpaReviewRepository = jpaReviewRepository;
        this.reviewFinderService = reviewFinderService;
        this.itemFinderService = itemFinderService;
        this.userFinderService = userFinderService;
    }

    @Transactional
    public Review update(ReviewRequest reviewRequest, Long id) {
        Review review = reviewFinderService.find(id);

        if (reviewRequest.getItemId() != null) {
            Item item = itemFinderService.find(reviewRequest.getItemId());
            review.setItem(item);
        }

        if (reviewRequest.getUserId() != null) {
            User user = userFinderService.find(reviewRequest.getUserId());
            review.setUser(user);
        }

        if (reviewRequest.getComment() != null) {
            review.setComment(reviewRequest.getComment());
        }
        if (reviewRequest.getRanking() != null) {
            review.setRanking(reviewRequest.getRanking());
        }
        if (reviewRequest.getPrice() != null) {
            review.setPrice(reviewRequest.getPrice());
        }
        if (reviewRequest.getDate() != null) {
            review.setDate(reviewRequest.getDate());
        }

        return jpaReviewRepository.save(review);
    }
}
