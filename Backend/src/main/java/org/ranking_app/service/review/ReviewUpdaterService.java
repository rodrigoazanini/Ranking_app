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

        Item item = itemFinderService.find(reviewRequest.getItemId());
        User user = userFinderService.find(reviewRequest.getUserId());

        review.setComment(reviewRequest.getComment());
        review.setRanking(reviewRequest.getRanking());
        review.setPrice(reviewRequest.getPrice());
        review.setItem(item);
        review.setUser(user);
        review.setDate(reviewRequest.getDate());

        return jpaReviewRepository.save(review);
    }
}
