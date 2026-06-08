package org.ranking_app.dto.response.item;

import org.ranking_app.model.item.Item;

public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private Double weight;
    private Double price_min;
    private Double price_max;
    private Boolean enabled;
    private Boolean suggested;
    private Long categoryId;
    private Long userId;

    public ItemResponse() {}

    public ItemResponse(
        Long id,
        String name, 
        String description,
        String brand,
        Double weight,
        Double price_min,
        Double price_max,
        Boolean enabled,
        Boolean suggested,
        Long categoryId,
        Long userId
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.weight = weight;
        this.price_min = price_min;
        this.price_max = price_max;
        this.enabled = enabled;
        this.suggested = suggested;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    static public ItemResponse fromEntity(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getBrand(),
                item.getWeigth(),
                item.getPrice_min(),
                item.getPrice_max(),
                item.getEnabled(),
                item.getSuggested(),
                item.getCategory() != null ? item.getCategory().getId() : null,
                item.getSuggested_by() != null ? item.getSuggested_by().getId() : null
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

    public String getBrand() { return brand; }

    public Double getWeight() { return weight; }

    public Double getPrice_min() {
        return price_min;
    }

    public Double getPrice_max() {
        return price_max;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getSuggested() {
        return suggested;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getUserId() {
        return userId;
    }

}
