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
public class ReviewCreatorService {
    private final JpaReviewRepository jpaReviewRepository;
    private final ItemFinderService itemFinderService;
    private final UserFinderService userFinderService;

    public ReviewCreatorService(
        JpaReviewRepository jpaReviewRepository,
        ItemFinderService itemFinderService,
        UserFinderService userFinderService
    ) {
        this.jpaReviewRepository = jpaReviewRepository;
        this.itemFinderService = itemFinderService;
        this.userFinderService = userFinderService;
    }

    @Transactional
    public Review create(ReviewRequest request) {
        Item item = itemFinderService.find(request.getItemId());
        User user = userFinderService.find(request.getUserId());

        Review review = Review.fromRequest(request, item, user);
        if (item.getPriceMin() != null){
            item.setPriceMin(review.getPrice());
        }
        return jpaReviewRepository.save(review);
    }
}
