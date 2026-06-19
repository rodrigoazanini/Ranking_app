package org.ranking_app.seed;

import org.ranking_app.dto.request.category.CategoryRequest;
import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.dto.request.review.ReviewRequest;
import org.ranking_app.dto.request.user.UserRequest;
import org.ranking_app.dto.request.user_item_favorite.UserItemFavoriteRequest;
import org.ranking_app.repository.category.JpaCategoryRepository;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.repository.review.JpaReviewRepository;
import org.ranking_app.repository.user.JpaUserRepository;
import org.ranking_app.repository.user_item_favorite.JpaUserItemFavoriteRepository;
import org.ranking_app.service.category.CategoryCreatorService;
import org.ranking_app.service.item.ItemCreatorService;
import org.ranking_app.service.review.ReviewCreatorService;
import org.ranking_app.service.user.UserCreatorService;
import org.ranking_app.service.user_item_favorite.UserItemFavoriteCreatorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class Seeder implements CommandLineRunner {

    private final UserCreatorService userCreatorService;
    private final CategoryCreatorService categoryCreatorService;
    private final ItemCreatorService itemCreatorService;
    private final ReviewCreatorService reviewCreatorService;
    private final UserItemFavoriteCreatorService userItemFavoriteCreatorService;
    private final JpaUserRepository jpaUserRepository;
    private final JpaCategoryRepository jpaCategoryRepository;
    private final JpaItemRepository jpaItemRepository;
    private final JpaReviewRepository jpaReviewRepository;
    private final JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository;

    public Seeder(
            UserCreatorService userCreatorService,
            CategoryCreatorService categoryCreatorService,
            ItemCreatorService itemCreatorService,
            ReviewCreatorService reviewCreatorService,
            UserItemFavoriteCreatorService userItemFavoriteCreatorService,
            JpaUserRepository jpaUserRepository,
            JpaCategoryRepository jpaCategoryRepository,
            JpaItemRepository jpaItemRepository,
            JpaReviewRepository jpaReviewRepository,
            JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository
    ) {
        this.userCreatorService = userCreatorService;
        this.categoryCreatorService = categoryCreatorService;
        this.itemCreatorService = itemCreatorService;
        this.reviewCreatorService = reviewCreatorService;
        this.userItemFavoriteCreatorService = userItemFavoriteCreatorService;
        this.jpaUserRepository = jpaUserRepository;
        this.jpaCategoryRepository = jpaCategoryRepository;
        this.jpaItemRepository = jpaItemRepository;
        this.jpaReviewRepository = jpaReviewRepository;
        this.jpaUserItemFavoriteRepository = jpaUserItemFavoriteRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (!databaseIsEmpty()) {
            return;
        }

        var adminUser = userCreatorService.create(new UserRequest(
                "admintest",
                "admin@test.com",
                "12345678",
                true
        ));

        var category = categoryCreatorService.create(new CategoryRequest(
                "Tecnologia"
        ));

        var item = itemCreatorService.create(new ItemRequest(
                "Demo item",
                "Seed item created for local development",
                "Seed brand",
                1.0,
                true,
                true,
                category.getId(),
                adminUser.getId()
        ));

        var review = reviewCreatorService.create(new ReviewRequest(
                "Seed review",
                4.5,
                1000.0,
                item.getId(),
                adminUser.getId(),
                new Date()
        ));

        item.setPriceMin(review.getPrice());
        item.setPriceMax(review.getPrice());
        item.setRankingAvg(review.getRanking());
        jpaItemRepository.save(item);

        userItemFavoriteCreatorService.create(new UserItemFavoriteRequest(
                item.getId(),
                adminUser.getId()
        ));
    }

    private boolean databaseIsEmpty() {
        return jpaUserRepository.count() == 0
                && jpaCategoryRepository.count() == 0
                && jpaItemRepository.count() == 0
                && jpaReviewRepository.count() == 0
                && jpaUserItemFavoriteRepository.count() == 0;
    }
}
