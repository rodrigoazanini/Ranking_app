package org.ranking_app.controller.item;

import jakarta.validation.Valid;
import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.model.item.Item;
import org.ranking_app.service.item.ItemCreatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/items")
public class ItemPostController {
    private final ItemCreatorService itemCreatorService;

    public ItemPostController(ItemCreatorService itemCreatorService) {
        this.itemCreatorService = itemCreatorService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(
            @Valid @RequestBody ItemRequest itemRequest
    ) {
        Item item =  itemCreatorService.create(itemRequest);

        ItemResponse itemResponse = ItemResponse.fromEntity(item);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemResponse);
    }
}
