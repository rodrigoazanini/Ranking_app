package org.ranking_app.controller.user_item_favorite;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.service.user_item_favorite.FavoriteRemoveByItemService;
import org.ranking_app.service.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user-item-favorites/item")
public class FavoriteRemoveByItemController {

    private final FavoriteRemoveByItemService favoriteRemoveByItemService;
    private final JwtService jwtService;

    public FavoriteRemoveByItemController(
            FavoriteRemoveByItemService favoriteRemoveByItemService,
            JwtService jwtService
    ) {
        this.favoriteRemoveByItemService = favoriteRemoveByItemService;
        this.jwtService = jwtService;
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeByItemId(
            @PathVariable Long itemId,
            HttpServletRequest httpRequest
    ) {
        Long userId = jwtService.extractUserId(httpRequest);
        favoriteRemoveByItemService.removeByUserIdAndItemId(userId, itemId);
        return ResponseEntity.noContent().build();
    }
}
