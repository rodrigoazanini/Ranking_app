package org.ranking_app.controller.item;

import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.service.item.ItemsGetTopByDateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/items/top/date")
public class ItemsGetTopByDateController {
    private final ItemsGetTopByDateService itemsGetTopByDateService;

    public ItemsGetTopByDateController(ItemsGetTopByDateService itemsGetTopByDateService) {
        this.itemsGetTopByDateService = itemsGetTopByDateService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getLatestItemsUploaded(
            @RequestParam(defaultValue = "10") int quantity,
            @RequestParam String date
    ) {
        Date parsedDate = parseDate(date);
        List<ItemResponse> response = itemsGetTopByDateService.findLatestItemsUploadedUpTo(parsedDate, quantity)
                .stream()
                .map(ItemResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response); // TODO change to itemSummaryResponse when its made
    }

    private Date parseDate(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
