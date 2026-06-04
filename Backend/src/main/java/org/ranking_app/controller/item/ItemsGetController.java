package org.ranking_app.controller.item;

import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.model.item.Item;
import org.ranking_app.service.item.ItemsSearcherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/items")
public class ItemsGetController {
    private final ItemsSearcherService itemsSearcherService;

    public ItemsGetController(
            ItemsSearcherService itemsSearcherService
    ) {
        this.itemsSearcherService = itemsSearcherService;
    }

    @GetMapping
    //items?page=2&size=5
    public ResponseEntity<Page<ItemResponse>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> items = itemsSearcherService.findAll(pageable);

        return ResponseEntity.ok(
                items.map(
                        product -> ItemResponse.fromEntity(product)
                )
        );
    }
}
