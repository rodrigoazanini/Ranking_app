package org.ranking_app.controller.item;

import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.service.item.ItemsGetTopByRankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/items/top/ranking")
public class ItemsGetTopByRankingController {
    private final ItemsGetTopByRankingService itemsGetTopByRankingService;

    public ItemsGetTopByRankingController(ItemsGetTopByRankingService itemsGetTopByRankingService) {
        this.itemsGetTopByRankingService = itemsGetTopByRankingService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getTopItemsByRanking(
            @RequestParam(defaultValue = "10") int quantity
    ) {
        List<ItemResponse> response = itemsGetTopByRankingService.findTopItemsByRanking(quantity)
                .stream()
                .map(ItemResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response); // TODO change to itemSummaryResponse when its made
    }
}
