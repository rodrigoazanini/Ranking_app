package org.ranking_app.dto.request.item;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.ranking_app.dto.response.category.CategoryResponse;

public class ItemRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "La descripcion es obligatoria")
    private String description;

    private String brand;

    private Double weight;

    @Nullable
    private Boolean enabled;

    @Nullable
    private Boolean suggested;

    @Nullable
    private Long categoryId;

    @Nullable
    private Long userId;

    public ItemRequest() {}
    public ItemRequest(
        String name, 
        String description,
        String brand,
        Double weight,
        Boolean enabled,
        Boolean suggested,
        Long categoryId,
        Long userId
    ) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.weight = weight;
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
