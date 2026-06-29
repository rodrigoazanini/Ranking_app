package org.ranking_app.controller.item;

import org.ranking_app.dto.response.item.ItemReviewsResponse;
import org.ranking_app.model.item.Item;
import org.ranking_app.service.item.ItemFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/items")
public class ItemGetController {

    private final ItemFinderService itemFinderService;

    public ItemGetController(ItemFinderService itemFinderService) {
        this.itemFinderService = itemFinderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemReviewsResponse> find(@PathVariable Long id) {
       Item item = itemFinderService.find(id);

       ItemReviewsResponse response = ItemReviewsResponse.fromEntity(item);

       return ResponseEntity.ok(response);
    }
}
