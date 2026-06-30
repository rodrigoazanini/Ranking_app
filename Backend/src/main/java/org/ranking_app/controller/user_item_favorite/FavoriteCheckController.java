package org.ranking_app.controller.user_item_favorite;

import org.ranking_app.dto.response.user_item_favorite.FavoriteCheckResponse;
import org.ranking_app.service.user_item_favorite.FavoriteCheckerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/favorites/check")
public class FavoriteCheckController {

    private final FavoriteCheckerService favoriteCheckerService;

    public FavoriteCheckController(FavoriteCheckerService favoriteCheckerService) {
        this.favoriteCheckerService = favoriteCheckerService;
    }

    @GetMapping
    public ResponseEntity<FavoriteCheckResponse> isFavorited(
            @RequestParam Long userId,
            @RequestParam Long itemId
    ) {
        FavoriteCheckResponse response = favoriteCheckerService.checkFavorite(userId, itemId);
        return ResponseEntity.ok(response);
    }
}
