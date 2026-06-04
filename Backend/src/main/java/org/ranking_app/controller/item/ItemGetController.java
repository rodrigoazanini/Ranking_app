package org.ranking_app.controller.item;

import org.ranking_app.dto.response.item.ItemResponse;
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
    public ResponseEntity<ItemResponse> find(@PathVariable Long id) {
       Item item = itemFinderService.find(id);

       ItemResponse response = ItemResponse.fromEntity(item);

       return ResponseEntity.ok(response);
    }
}
