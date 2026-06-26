package org.ranking_app.seed;

import org.ranking_app.dto.request.category.CategoryRequest;
import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.dto.request.review.ReviewRequest;
import org.ranking_app.dto.request.user.UserRequest;
import org.ranking_app.dto.request.user_item_favorite.UserItemFavoriteRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Seeder implements CommandLineRunner {
    private static final String[] CATEGORY_NAMES = {
            "dulces",
            "snacks",
            "caramelos",
            "chocolates",
            "chicles",
            "gomitas",
            "chupetines",
            "paletas",
            "confites",
            "alfajores"
    };

    private static final String[] ITEM_NAMES = {
            "Chocolatina Milka",
            "Chocolate Arcor",
            "Alfajor Jorgito",
            "Alfajor Havanna",
            "Gomitas Fini",
            "Gomitas Mentos",
            "Chupetin Vizzio",
            "Chupetin Pico Dulce",
            "Confite Sugus",
            "Confite M and Ms",
            "Caramelo Bon o Bon",
            "Caramelo Mantecol",
            "Paleta Ricolino",
            "Paleta Vizzio",
            "Chicle Bubbaloo",
            "Chicle Adams",
            "Lays",
            "Doritos",
            "Dulce de leche La Serenisima",
            "Dulce de leche La Paulina"
    };

    private static final String[] REVIEW_COMMENTS = {
            "Great taste",
            "Good value",
            "Would buy again",
            "Solid product",
            "Very sweet",
            "Nice texture",
            "Fresh and simple",
            "Recommended",
            "Average but fine",
            "Happy with it"
    };

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
    private final PasswordEncoder passwordEncoder;

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
            JpaUserItemFavoriteRepository jpaUserItemFavoriteRepository,
            PasswordEncoder passwordEncoder
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
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (!databaseIsEmpty()) {
            return;
        }

        User adminUser = User.fromRequest(new UserRequest(
                "admin",
                "admin@test.com",
                passwordEncoder.encode("12345678"),
                true
        ));
        adminUser.setAdmin(true);
        jpaUserRepository.save(adminUser);

        List<User> users = new ArrayList<>();
        users.add(adminUser);
        for (int i = 1; i <= 9; i++) {
            users.add(userCreatorService.create(new UserRequest(
                    "user" + i,
                    "user" + i + "@test.com",
                    "12345678",
                    false
            )));
        }

        List<Category> categories = new ArrayList<>();
        for (String categoryName : CATEGORY_NAMES) {
            categories.add(categoryCreatorService.create(new CategoryRequest(categoryName)));
        }

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < ITEM_NAMES.length; i++) {
            Category category = categories.get(ThreadLocalRandom.current().nextInt(categories.size()));
            Item item = itemCreatorService.create(
                    new ItemRequest(
                            ITEM_NAMES[i],
                            "Description for " + ITEM_NAMES[i],
                            "Brand " + (i + 1),
                            randomWeight(),
                            true,
                            ThreadLocalRandom.current().nextBoolean(),
                            "http://localhost:8091/Backend/uploads/images/item_default_backend.png",
                            category.getId(),
                            null
                    ),
                    adminUser
            );
            items.add(item);
        }

        List<Item> reviewItems = new ArrayList<>(items);
        Collections.shuffle(reviewItems);
        for (int i = 0; i < 20; i++) {
            Item item = reviewItems.get(i % reviewItems.size());
            User reviewer = users.get(ThreadLocalRandom.current().nextInt(users.size()));

            reviewCreatorService.create(new ReviewRequest(
                    randomReviewComment(i),
                    randomRanking(),
                    randomPrice(),
                    item.getId(),
                    reviewer.getId(),
                    randomRecentDate()
            ));
        }

        userItemFavoriteCreatorService.create(new UserItemFavoriteRequest(
                items.getFirst().getId(),
                adminUser.getId()
        ));
    }

    private String randomReviewComment(int index) {
        String base = REVIEW_COMMENTS[ThreadLocalRandom.current().nextInt(REVIEW_COMMENTS.length)];
        return base + " #" + (index + 1);
    }

    private Double randomRanking() {
        return 1.0 + (ThreadLocalRandom.current().nextInt(9) * 0.5);
    }

    private Double randomPrice() {
        return round(ThreadLocalRandom.current().nextDouble(50.0, 500.0), 2);
    }

    private Double randomWeight() {
        return round(ThreadLocalRandom.current().nextDouble(100.0, 1000.0), 1);
    }

    private Double round(double value, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }

    private Date randomRecentDate() {
        long daysAgo = ThreadLocalRandom.current().nextLong(0, 30);
        return Date.from(Instant.now().minus(daysAgo, ChronoUnit.DAYS));
    }

    private boolean databaseIsEmpty() {
        return jpaUserRepository.count() == 0
                && jpaCategoryRepository.count() == 0
                && jpaItemRepository.count() == 0
                && jpaReviewRepository.count() == 0
                && jpaUserItemFavoriteRepository.count() == 0;
    }
}
