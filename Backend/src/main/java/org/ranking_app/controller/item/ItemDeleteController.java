package org.ranking_app.controller.item;

import org.ranking_app.service.item.ItemDeleterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/items")
public class ItemDeleteController {
    private final ItemDeleterService itemDeleterService;

    public ItemDeleteController(ItemDeleterService itemDeleterService) {
        this.itemDeleterService = itemDeleterService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemDeleterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
