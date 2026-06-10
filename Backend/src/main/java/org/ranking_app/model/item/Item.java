package org.ranking_app.model.item;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.ranking_app.dto.request.item.ItemRequest;
import org.ranking_app.model.category.Category;
import org.ranking_app.model.user.User;

@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column()
    private String brand;

    @Column(nullable = false)
    private Double weigth;

    @Nullable
    @Column(nullable = true)
    private Double priceMin;

    @Nullable
    @Column(nullable = true)
    private Double priceMax;

    @Nullable
    @Column(nullable = true)
    private Double rankingAvg;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Boolean suggested;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            foreignKey = @ForeignKey(name = "fk_item_category")
    )
    private Category category;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_item_suggested_by_user")
    )
    private User suggested_by;

    public Item() {}

    public Item(
        Long id,
        String name, 
        String description,
        String brand,
        Double weight,
        Boolean enabled,
        Boolean suggested,
        Category category,
        User suggested_by
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.weigth = weight;
        this.enabled = enabled;
        this.suggested = suggested;
        this.category = category;
        this.suggested_by = suggested_by;
    }

    static public Item fromRequest(ItemRequest request) {
        return new Item(
            null,
            request.getName(),
            request.getDescription(),
            request.getBrand(),
            request.getWeight(),
            request.getEnabled(),
            request.getSuggested(),
            null,
            null
        );
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getWeigth() {
        return weigth;
    }
    public void setWeigth(Double weigth) {
        this.weigth = weigth;
    }

    public Double getPriceMin() {
        return priceMin;
    }
    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }
    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public Double getRankingAvg() {
        return rankingAvg;
    }
    public void setRankingAvg(Double rankingAvg) {
        this.rankingAvg = rankingAvg;
    }

    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getSuggested() {
        return suggested;
    }
    public void setSuggested(Boolean suggested) {
        this.suggested = suggested;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public User getSuggested_by() {
        return suggested_by;
    }
    public void setSuggested_by(User suggested_by) {
        this.suggested_by = suggested_by;
    }
}
