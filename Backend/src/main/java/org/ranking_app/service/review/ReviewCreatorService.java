package org.ranking_app.service.review;

import org.ranking_app.dto.request.review.ReviewRequest;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.review.Review;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.review.JpaReviewRepository;
import org.ranking_app.repository.review.ReviewItemStatsProjection;
import org.ranking_app.service.item.ItemFinderService;
import org.ranking_app.service.user.UserFinderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class ReviewCreatorService {
    private final JpaReviewRepository jpaReviewRepository;
    private final ItemFinderService itemFinderService;
    private final UserFinderService userFinderService;

    public ReviewCreatorService(
            JpaReviewRepository jpaReviewRepository,
            ItemFinderService itemFinderService,
            UserFinderService userFinderService) {
        this.jpaReviewRepository = jpaReviewRepository;
        this.itemFinderService = itemFinderService;
        this.userFinderService = userFinderService;
    }

    @Transactional
    public Review create(ReviewRequest request) {
        Item item = itemFinderService.find(request.getItemId());
        User user = userFinderService.find(request.getUserId());
        List<Review> existingReviews = jpaReviewRepository.findReviewsByUserIdAndItemId(user.getId(), item.getId());

        Date cutoffReviewedDate = Date.from(
                LocalDate.now()
                        .minusDays(7)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant());

        if (!existingReviews.isEmpty() &&
                existingReviews.stream().anyMatch(r -> r.getDate().after(cutoffReviewedDate))) {
            throw new IllegalArgumentException(
                    "Ya has creado una reseña para este artículo en los últimos 7 días.");
        }

        Review review = Review.fromRequest(request, item, user);
        Review savedReview = jpaReviewRepository.saveAndFlush(review);

        LocalDate cutoffDate = LocalDate.now().minusDays(30);
        ReviewItemStatsProjection stats = jpaReviewRepository.findItemStatsByItemId(item.getId(), cutoffDate);

        Double priceMin = (stats != null && stats.getPriceMin() != null) ? stats.getPriceMin() : item.getPriceMin();
        if (priceMin == null)
            priceMin = savedReview.getPrice();

        Double priceMax = (stats != null && stats.getPriceMax() != null) ? stats.getPriceMax() : item.getPriceMax();
        if (priceMax == null)
            priceMax = savedReview.getPrice();

        Double rankingAvg = (stats != null && stats.getRankingAvg() != null) ? stats.getRankingAvg()
                : item.getRankingAvg();
        if (rankingAvg == null)
            rankingAvg = savedReview.getRanking();

        item.setPriceMin(priceMin);
        item.setPriceMax(priceMax);
        item.setRankingAvg(rankingAvg);

        return savedReview;
    }
}
