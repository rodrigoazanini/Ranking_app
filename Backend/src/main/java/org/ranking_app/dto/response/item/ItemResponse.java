package org.ranking_app.dto.response.item;

import org.ranking_app.dto.response.category.CategoryResponse;
import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.item.Item;

public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private Double weight;
    private Double priceMin;
    private Double priceMax;
    private Boolean enabled;
    private Boolean suggested;
    private CategoryResponse categoryResponse;
    private UserResponse userResponse;

    public ItemResponse() {}

    public ItemResponse(
        Long id,
        String name, 
        String description,
        String brand,
        Double weight,
        Double priceMin,
        Double priceMax,
        Boolean enabled,
        Boolean suggested,
        CategoryResponse categoryResponse,
        UserResponse userResponse
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.weight = weight;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.enabled = enabled;
        this.suggested = suggested;
        this.categoryResponse = categoryResponse;
        this.userResponse = userResponse;
    }

    static public ItemResponse fromEntity(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getBrand(),
                item.getWeigth(),
                item.getPriceMin(),
                item.getPriceMax(),
                item.getEnabled(),
                item.getSuggested(),
                CategoryResponse.fromEntity(item.getCategory()),
                UserResponse.fromEntity(item.getSuggested_by())
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

    public Double getPriceMin() {
        return priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getSuggested() {
        return suggested;
    }

    public  CategoryResponse getCategoryResponse() {
        return categoryResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

}
