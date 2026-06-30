package org.ranking_app.dto.response.item;

public class ItemSummaryResponse {
    private Long id;
    private String name;
    private String imageUrl;

    public ItemSummaryResponse() {}

    public ItemSummaryResponse(
        Long id,
        String name,
        String imageUrl
    ) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    static public ItemSummaryResponse fromEntity(org.ranking_app.model.item.Item item) {
        return new ItemSummaryResponse(
                item.getId(),
                item.getName(),
                item.getImageUrl()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
