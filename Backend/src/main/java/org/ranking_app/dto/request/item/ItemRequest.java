package org.ranking_app.dto.request.item;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class ItemRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "La descripcion es obligatoria")
    private String description;

    private String brand;

    private Double weight;

    @NotNull(message = "El precio es obligatorio")
    private Double price_min;

    private Double price_max;

    @Nullable
    private Double ranking_avg; // Crear trigger the update?

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
        Double price_min,
        Double price_max,
        Double ranking_avg,
        Boolean enabled,
        Boolean suggested,
        Long categoryId,
        Long userId
    ) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.weight = weight;
        this.price_min = price_min;
        this.price_max = price_max;
        this.ranking_avg = ranking_avg;
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

    public Double getPrice_min() {
        return price_min;
    }

    public Double getPrice_max() {
        return price_max;
    }

    public Double getRanking_avg() {
        return ranking_avg;
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
