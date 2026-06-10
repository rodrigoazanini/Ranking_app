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

import java.time.LocalDate;

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

        // Estuve pensando y no tendria sentido mostrar el min y max historico dada la inflacion de argentina jaja saludos
        LocalDate cutoffDate = LocalDate.now().minusDays(30);
        ReviewItemStatsProjection stats = jpaReviewRepository.findItemStatsByItemId(item.getId(), cutoffDate);

        Review review = Review.fromRequest(request, item, user);

//        if (item.getPriceMin() == null || item.getPriceMin() > review.getPrice()){
//            item.setPriceMin(review.getPrice());
//        }
//        if (item.getPriceMax() == null || item.getPriceMax() < review.getPrice()){
//            item.setPriceMax(review.getPrice());
//        }

        item.setPriceMin(stats != null ? stats.getPriceMin() : review.getPrice());
        item.setPriceMax(stats != null ? stats.getPriceMax() : review.getPrice());
        item.setRankingAvg(stats != null ? stats.getRankingAvg() : review.getRanking());

        Review savedReview = jpaReviewRepository.saveAndFlush(review);

        return savedReview;
    }
}
