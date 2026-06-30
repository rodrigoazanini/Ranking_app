package org.ranking_app.dto.response.item;

import org.ranking_app.dto.response.category.CategoryResponse;
import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.item.Item;

public class ItemResponse extends ItemSummaryResponse {
    private String description;
    private String brand;
    private Double weight;
    private Double priceMin;
    private Double priceMax;
    private Double rankingAvg;
    private Boolean enabled;
    private Boolean suggested;
    private CategoryResponse categoryResponse;
    private UserResponse userResponse;

    public ItemResponse() {}

    public ItemResponse(
        Long id,
        String name,
        String imageUrl,
        String description,
        String brand,
        Double weight,
        Double priceMin,
        Double priceMax,
        Double rankingAvg,
        Boolean enabled,
        Boolean suggested,
        CategoryResponse categoryResponse,
        UserResponse userResponse
    ) {
        super(id, name, imageUrl);
        this.description = description;
        this.brand = brand;
        this.weight = weight;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.rankingAvg = rankingAvg;
        this.enabled = enabled;
        this.suggested = suggested;
        this.categoryResponse = categoryResponse;
        this.userResponse = userResponse;
    }

    static public ItemResponse fromEntity(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getImageUrl(),
                item.getDescription(),
                item.getBrand(),
                item.getWeigth(),
                item.getPriceMin(),
                item.getPriceMax(),
                item.getRankingAvg(),
                item.getEnabled(),
                item.getSuggested(),
                item.getCategory() != null ? CategoryResponse.fromEntity(item.getCategory()) : null,
                item.getSuggested_by() != null ? UserResponse.fromEntity(item.getSuggested_by()) : null
        );
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() { 
        return brand; 
    }

    public Double getWeight() { 
        return weight;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public Double getRankingAvg() {
        return rankingAvg;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getSuggested() {
        return suggested;
    }

    public CategoryResponse getCategoryResponse() {
        return categoryResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }
}
