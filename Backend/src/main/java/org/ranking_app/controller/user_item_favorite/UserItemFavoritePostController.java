package org.ranking_app.controller.user_item_favorite;

import jakarta.validation.Valid;
import org.ranking_app.dto.request.user_item_favorite.UserItemFavoriteRequest;
import org.ranking_app.dto.response.user_item_favorite.UserItemFavoriteResponse;
import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.ranking_app.service.user_item_favorite.UserItemFavoriteCreatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user-item-favorites")
public class UserItemFavoritePostController {
    private final UserItemFavoriteCreatorService userItemFavoriteCreatorService;

    public UserItemFavoritePostController(
        UserItemFavoriteCreatorService userItemFavoriteCreatorService
    ) {
        this.userItemFavoriteCreatorService = userItemFavoriteCreatorService;
    }

    @PostMapping
    public ResponseEntity<UserItemFavoriteResponse> create(
        @Valid @RequestBody UserItemFavoriteRequest userItemFavoriteRequest
    ) {
        UserItemFavorite userItemFavorite = userItemFavoriteCreatorService.create(userItemFavoriteRequest);

        UserItemFavoriteResponse userItemFavoriteResponse = UserItemFavoriteResponse.fromEntity(userItemFavorite);

        return ResponseEntity.status(HttpStatus.CREATED).body(userItemFavoriteResponse);
    }
}
