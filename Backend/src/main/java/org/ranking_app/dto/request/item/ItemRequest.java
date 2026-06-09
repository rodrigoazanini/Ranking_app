package org.ranking_app.dto.request.item;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ItemRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "La descripcion es obligatoria")
    private String description;

    private String brand;

    private Double weight;

    @NotNull(message = "El precio es obligatorio")
    private Double priceMin;

    private Double priceMax;

    @Nullable
    private Double rankingAvg; // Crear trigger the update?

    @Nullable
    private Boolean enabled;

    @Nullable
    private Boolean suggested;

    @NotNull(message = "La categoria es obligatoria")
    private Long categoryId;

    @Nullable
    private Long userId;

    public ItemRequest() {}
    public ItemRequest(
        String name, 
        String description,
        String brand,
        Double weight,
        Double priceMin,
        Double priceMax,
        Double rankingAvg,
        Boolean enabled,
        Boolean suggested,
        Long categoryId,
        Long userId
    ) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.weight = weight;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.rankingAvg = rankingAvg;
        this.enabled = enabled;
        this.suggested = suggested;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public String getName() {
        return name;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getUserId() {
        return userId;
    }
}
