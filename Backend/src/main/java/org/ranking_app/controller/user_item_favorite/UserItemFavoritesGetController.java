package org.ranking_app.controller.user_item_favorite;

import org.ranking_app.dto.response.user_item_favorite.UserItemFavoriteResponse;
import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.ranking_app.service.user_item_favorite.UserItemFavoritesSearcherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user-item-favorites")
public class UserItemFavoritesGetController {
    private final UserItemFavoritesSearcherService userItemFavoritesSearcherService;

    public UserItemFavoritesGetController(
        UserItemFavoritesSearcherService userItemFavoritesSearcherService
    ) {
        this.userItemFavoritesSearcherService = userItemFavoritesSearcherService;
    }

    @GetMapping
    public ResponseEntity<Page<UserItemFavoriteResponse>> search(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserItemFavorite> userItemFavorites = userItemFavoritesSearcherService.findAll(pageable);

        return ResponseEntity.ok(
            userItemFavorites.map(UserItemFavoriteResponse::fromEntity)
        );
    }
}
