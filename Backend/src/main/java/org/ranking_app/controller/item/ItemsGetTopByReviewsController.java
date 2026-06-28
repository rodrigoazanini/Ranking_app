package org.ranking_app.controller.item;

import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.service.item.ItemsGetTopByReviewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/items/top/reviews")
public class ItemsGetTopByReviewsController {
    private final ItemsGetTopByReviewsService itemsGetTopByReviewsService;

    public ItemsGetTopByReviewsController(ItemsGetTopByReviewsService itemsGetTopByReviewsService) {
        this.itemsGetTopByReviewsService = itemsGetTopByReviewsService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getTopItemsByReviewCount(
            @RequestParam(defaultValue = "10") int quantity
    ) {
        List<ItemResponse> response = itemsGetTopByReviewsService.findTopItemsByReviewCount(quantity)
                .stream()
                .map(ItemResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }
}
