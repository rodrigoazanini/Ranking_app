package org.ranking_app.seed;

import org.ranking_app.dto.request.category.CategoryRequest;
import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.dto.request.review.ReviewRequest;
import org.ranking_app.dto.request.user.UserRequest;
import org.ranking_app.dto.request.user_item_favorite.UserItemFavoriteRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.review.Review;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.category.JpaCategoryRepository;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.repository.review.JpaReviewRepository;
import org.ranking_app.repository.user.JpaUserRepository;
import org.ranking_app.repository.user_item_favorite.JpaUserItemFavoriteRepository;
import org.ranking_app.service.category.CategoryCreatorService;
import org.ranking_app.service.item.ItemCreatorService;
import org.ranking_app.service.item.ItemUpdaterService;
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
            "alfajores",
            "Barras de cereal",
            "Bombones",
            "caramelos",
            "chicles",
            "chocolates",
            "chupetines",
            "confites",
            "dulces",
            "gomitas",
            "Obleas",
            "paletas",
            "pastillas de menta",
            "snacks",
            "Turrones"
    };

    private static final String[] ITEM_NAMES = {
            "Alfajor Jorgito",
            "Alfajor Havanna",

            "Barra de cereal Cereal Mix",
            "Barra de cereal Nature Valley",

            "Bombón Bon o Bon",
            "Bombón Ferrero Rocher",

            "Caramelo Media Hora",
            "Caramelo Sugus",

            "Chicle Beldent",
            "Chicle Topline",

            "Chocolate Águila",
            "Chocolate Milka",

            "Chupetín Pico Dulce",
            "Chupetín Billiken",

            "Confites Rocklets",
            "Confites M&M's",

            "Dulce de leche Mardel",
            "Dulce de membrillo",

            "Gomitas Mogul",
            "Gomitas Fini",

            "Oblea Bonobon Wafer",
            "Oblea Arcor",

            "Paleta Pico Dulce",
            "Paleta Flynn Paff",

            "Pastillas de menta DRF",
            "Pastillas de menta Menthoplus",

            "Snack Palitos Salados",
            "Snack Papas Lays",

            "Turrón Arcor",
            "Turrón Georgalos"
    };

    private static final String[] ITEM_DESCRIPTIONS = {
            "Alfajor clásico con relleno de dulce de leche y cobertura de chocolate.",
            "Alfajor premium elaborado con ingredientes de alta calidad y un sabor tradicional.",

            "Barra de cereal con avena y frutas, ideal para un snack saludable.",
            "Barra energética con cereales integrales y miel para consumir en cualquier momento.",

            "Bombón de chocolate relleno con crema y maní, de textura suave.",
            "Bombón de chocolate con avellana entera y relleno cremoso.",

            "Caramelo duro de sabor intenso y larga duración.",
            "Caramelo masticable con sabores frutales y textura suave.",

            "Chicle de larga duración con refrescante sabor a menta.",
            "Chicle sin azúcar con sabor intenso y fresco.",

            "Chocolate con leche de textura cremosa y sabor equilibrado.",
            "Chocolate elaborado con cacao de calidad y un suave toque dulce.",

            "Chupetín con relleno ácido y sabores frutales variados.",
            "Chupetín clásico ideal para disfrutar en cualquier ocasión.",

            "Confites de chocolate recubiertos con una capa crocante de colores.",
            "Grageas de chocolate con cobertura crujiente y diferentes sabores.",

            "Dulce tradicional de leche con textura cremosa y sabor intenso.",
            "Dulce elaborado con membrillo seleccionado, ideal para postres o meriendas.",

            "Gomitas masticables con sabores frutales y una textura suave y divertida.",
            "Gomitas de sabores variados con colores vibrantes y una consistencia tierna.",

            "Obleas rellenas con crema de cacao y cubiertas con chocolate.",
            "Oblea liviana y crocante con un delicioso relleno dulce.",

            "Paleta de caramelo con sabores frutales y un toque ácido.",
            "Paleta clásica de caramelo duro con sabor intenso y duradero.",

            "Pastillas refrescantes de menta para un aliento fresco.",
            "Pastillas sabor menta con efecto refrescante de larga duración.",

            "Snack salado y crocante, ideal para compartir o disfrutar entre comidas.",
            "Papas fritas crocantes elaboradas con ingredientes seleccionados.",

            "Turrón de maní con textura crocante y sabor tradicional.",
            "Turrón clásico elaborado con maní tostado y miel."
    };

    private static final String[] ITEM_BRAND = {
            "Jorgito",
            "Havanna",

            "Cereal Mix",
            "Nature Valley",

            "Arcor",
            "Ferrero",

            "Media Hora",
            "Arcor",

            "Beldent",
            "Topline",

            "Águila",
            "Milka",

            "Pico Dulce",
            "Billiken",

            "Rocklets",
            "M&M's",

            "La Serenísima",
            "Ledesma",

            "Arcor",
            "Bon o Bon",

            "Mogul",
            "Fini",

            "Pico Dulce",
            "Flynn Paff",

            "DRF",
            "Menthoplus",

            "Lay's",
            "Pehuamar",

            "Arcor",
            "Georgalos"
    };

    private static final String[] REVIEW_COMMENTS = {
            "Buen sabor.",
            "Buena relación calidad-precio.",
            "Lo compraria otra vez.",
            "Muy dulce",
            "Buena textura",
            "Fresco y sencillo",
            "Recomendado",
            "Excelente producto, superó mis expectativas.",
            "Muy buena calidad, lo recomiendo.",
            "Cumple perfectamente con lo que promete.",
            "La relación calidad-precio es excelente.",
            "Llegó en perfectas condiciones y funciona muy bien.",
            "No está mal, pero esperaba un poco más.",
            "El diseño es muy bonito y práctico.",
            "Después de varias semanas de uso sigue como nuevo.",
            "Fácil de usar y de muy buena calidad.",
            "El envío fue rápido y el producto excelente.",
            "Es aceptable, aunque tiene algunos detalles mejorables.",
            "No cumplió del todo con mis expectativas.",
            "La calidad podría ser mejor por el precio.",
            "Tuve algunos problemas al principio, pero luego funcionó bien.",
            "Muy satisfecho con la compra.",
            "Definitivamente volvería a comprar este producto.",
            "El acabado es excelente y se siente resistente.",
            "No lo recomendaría, esperaba una mejor experiencia.",
            "Buen producto, aunque el precio es un poco elevado.",
            "Cinco estrellas, volvería a comprar sin dudarlo."
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
            PasswordEncoder passwordEncoder,
            ItemUpdaterService itemUpdaterService) {
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
                true));
        adminUser.setAdmin(true);
        jpaUserRepository.save(adminUser);

        List<User> users = new ArrayList<>();
        users.add(adminUser);
        for (int i = 1; i <= 9; i++) {
            users.add(userCreatorService.create(new UserRequest(
                    "user" + i,
                    "user" + i + "@test.com",
                    "12345678",
                    false)));
        }

        List<Category> categories = new ArrayList<>();
        for (String categoryName : CATEGORY_NAMES) {
            categories.add(categoryCreatorService.create(new CategoryRequest(categoryName)));
        }

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < ITEM_NAMES.length; i++) {
            Category category = categories.get(i / 2);
            Item item = itemCreatorService.create(
                    new ItemRequest(
                            ITEM_NAMES[i],
                            ITEM_DESCRIPTIONS[i],
                            ITEM_BRAND[i],
                            randomWeight(),
                            true,
                            ThreadLocalRandom.current().nextBoolean(),
                            "http://localhost:8091/uploads/images/item_default_backend.jpg",
                            category.getId(),
                            null),
                    adminUser);
            items.add(item);
        }

        List<Item> reviewItems = new ArrayList<>(items);
        Collections.shuffle(reviewItems);
        for (int i = 0; i < 20; i++) {
            Item item = reviewItems.get(i % reviewItems.size());
            User reviewer = users.get(ThreadLocalRandom.current().nextInt(users.size()));
            Double randomRanking = randomRanking();
            Double randomPrice = randomPrice();
            reviewCreatorService.create(new ReviewRequest(
                    randomReviewComment(i),
                    randomRanking,
                    randomPrice,
                    item.getId(),
                    reviewer.getId(),
                    randomRecentDate()));
        }
        jpaItemRepository.saveAll(reviewItems);

        userItemFavoriteCreatorService.create(new UserItemFavoriteRequest(
                items.getFirst().getId(),
                adminUser.getId()));

        userItemFavoriteCreatorService.create(new UserItemFavoriteRequest(
                items.get(1).getId(),
                users.get(1).getId()));
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
