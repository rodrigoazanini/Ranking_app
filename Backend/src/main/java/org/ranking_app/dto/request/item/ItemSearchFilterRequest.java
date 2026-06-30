package org.ranking_app.dto.request.item;

import jakarta.annotation.Nullable;

public class ItemSearchFilterRequest extends ItemSearchRequest {

    @Nullable
    private String brand;

    @Nullable
    private Boolean suggested;

    @Nullable
    private Boolean enabled;

    @Nullable
    private String category;

    public ItemSearchFilterRequest() {}

    public ItemSearchFilterRequest(
        String query,
        String brand,
        Boolean suggested,
        Boolean enabled,
        String category
    ) {
        super(query);
        this.brand = brand;
        this.suggested = suggested;
        this.enabled = enabled;
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getSuggested() {
        return suggested;
    }

    public void setSuggested(Boolean suggested) {
        this.suggested = suggested;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}