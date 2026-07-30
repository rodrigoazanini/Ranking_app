package org.ranking_app.controller.item;

import org.ranking_app.dto.request.item.ItemSearchFilterRequest;
import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.model.item.Item;
import org.ranking_app.service.item.ItemsSearcherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/items/search/filter")
public class ItemsGetSearchFilterController {
    private final ItemsSearcherService itemsSearcherService;

    public ItemsGetSearchFilterController(ItemsSearcherService itemsSearcherService) {
        this.itemsSearcherService = itemsSearcherService;
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponse>> search(
            @Valid @ModelAttribute ItemSearchFilterRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest httpRequest
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> items = itemsSearcherService.search(request, pageable, httpRequest);

        return ResponseEntity.ok(
                items.map(ItemResponse::fromEntity)
        );
    }
}
