package org.ranking_app.controller.item;

import jakarta.validation.Valid;
import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.model.item.Item;
import org.ranking_app.service.item.ItemUpdaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/items")
public class ItemPutController {
    private final ItemUpdaterService itemUpdaterService;

    public ItemPutController(
            ItemUpdaterService itemUpdaterService
    ) {
        this.itemUpdaterService = itemUpdaterService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ItemRequest request
    ) {
        Item item = itemUpdaterService.update(request, id);

        ItemResponse response = ItemResponse.fromEntity(item);

        return ResponseEntity.ok(response);
    }
}
