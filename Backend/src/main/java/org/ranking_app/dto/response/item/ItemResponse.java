package org.ranking_app.dto.response.item;

import org.ranking_app.model.item.Item;

public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean active;
    private Long categoryId;

    public ItemResponse() {}

    public ItemResponse(
        Long id,
        String name, 
        String description, 
        Double price, 
        Boolean active, 
        Long categoryId
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.categoryId = categoryId;
    }

    static public ItemResponse fromEntity(Item item) {
        return new ItemResponse(
            item.getId(), item.getName(), item.getDescription(),
                item.getPrice(),
                item.getActive(),
                item.getCategory() != null ? item.getCategory().getId() : null
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getActive() {
        return active;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
