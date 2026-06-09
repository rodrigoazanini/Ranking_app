package org.ranking_app.controller.user_item_favorite;

import org.ranking_app.service.user_item_favorite.UserItemFavoriteDeleterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user-item-favorites")
public class UserItemFavoriteDeleteController {
    private final UserItemFavoriteDeleterService userItemFavoriteDeleterService;

    public UserItemFavoriteDeleteController(
        UserItemFavoriteDeleterService userItemFavoriteDeleterService
    ) {
        this.userItemFavoriteDeleterService = userItemFavoriteDeleterService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userItemFavoriteDeleterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
