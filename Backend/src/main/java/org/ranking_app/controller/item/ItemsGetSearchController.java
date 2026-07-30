package org.ranking_app.controller.item;

import org.ranking_app.dto.request.item.ItemSearchRequest;
import org.ranking_app.dto.response.item.ItemSummaryResponse;
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
@RequestMapping("api/items/search")
public class ItemsGetSearchController {
    private final ItemsSearcherService itemsSearcherService;

    public ItemsGetSearchController(ItemsSearcherService itemsSearcherService) {
        this.itemsSearcherService = itemsSearcherService;
    }

    @GetMapping
    public ResponseEntity<Page<ItemSummaryResponse>> search(
            @Valid ItemSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpServletRequest httpRequest
    ) {
        if (request.getQuery() == null || request.getQuery().isBlank()) {
            throw new IllegalArgumentException("La consulta es obligatoria");
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> items = itemsSearcherService.search(request, pageable, httpRequest);

        return ResponseEntity.ok(
                items.map(ItemSummaryResponse::fromEntity)
        );
    }
}
