package org.ranking_app.controller.user_item_favorite;

import org.ranking_app.dto.response.user_item_favorite.UserItemFavoriteResponse;
import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.ranking_app.service.user_item_favorite.UserItemFavoriteFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user-item-favorites")
public class UserItemFavoriteGetController {

    private final UserItemFavoriteFinderService userItemFavoriteFinderService;

    public UserItemFavoriteGetController(
        UserItemFavoriteFinderService userItemFavoriteFinderService
    ) {
        this.userItemFavoriteFinderService = userItemFavoriteFinderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserItemFavoriteResponse> find(@PathVariable Long id) {
        UserItemFavorite userItemFavorite = userItemFavoriteFinderService.find(id);

        UserItemFavoriteResponse response = UserItemFavoriteResponse.fromEntity(userItemFavorite);

        return ResponseEntity.ok(response);
    }
}
