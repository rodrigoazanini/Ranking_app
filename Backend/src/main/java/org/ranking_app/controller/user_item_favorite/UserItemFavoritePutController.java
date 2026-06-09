package org.ranking_app.controller.user_item_favorite;

import jakarta.validation.Valid;
import org.ranking_app.dto.request.user_item_favorite.UserItemFavoriteRequest;
import org.ranking_app.dto.response.user_item_favorite.UserItemFavoriteResponse;
import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.ranking_app.service.user_item_favorite.UserItemFavoriteUpdaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user-item-favorites")
public class UserItemFavoritePutController {
    private final UserItemFavoriteUpdaterService userItemFavoriteUpdaterService;

    public UserItemFavoritePutController(
        UserItemFavoriteUpdaterService userItemFavoriteUpdaterService
    ) {
        this.userItemFavoriteUpdaterService = userItemFavoriteUpdaterService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserItemFavoriteResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody UserItemFavoriteRequest request
    ) {
        UserItemFavorite userItemFavorite = userItemFavoriteUpdaterService.update(request, id);

        UserItemFavoriteResponse response = UserItemFavoriteResponse.fromEntity(userItemFavorite);

        return ResponseEntity.ok(response);
    }
}
