package org.ranking_app.controller.item;

import org.ranking_app.dto.response.item.ItemReviewsResponse;
import org.ranking_app.model.item.Item;
import org.ranking_app.service.item.ItemsSearcherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/items/latest")
public class ItemsGetLatestController {
    private final ItemsSearcherService itemsSearcherService;

    public ItemsGetLatestController(
            ItemsSearcherService itemsSearcherService
    ) {
        this.itemsSearcherService = itemsSearcherService;
    }

    @GetMapping
    public ResponseEntity<Page<ItemReviewsResponse>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Item> items = itemsSearcherService.findAll(pageable);

        return ResponseEntity.ok(
                items.map(
                        item -> ItemReviewsResponse.fromEntity(item)
                )
        );
    }
}
