package org.ranking_app.dto.response.review;

import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.model.review.Review;
import java.util.Date;

public class ReviewItemSummaryResponse extends ReviewSummaryResponse {
    private ItemResponse item;

    public ReviewItemSummaryResponse() {}

    public ReviewItemSummaryResponse(
        Long id,
        String comment,
        Double ranking,
        Double price,
        Date date,
        ItemResponse item
    ) {
        super(id, comment, ranking, price, date);
        this.item = item;
    }

    static public ReviewItemSummaryResponse fromEntity(Review review) {
        return new ReviewItemSummaryResponse(
            review.getId(),
            review.getComment(),
            review.getRanking(),
            review.getPrice(),
            review.getDate(),
            ItemResponse.fromEntity(review.getItem())
        );
    }

    public ItemResponse getItem() {
        return item;
    }

    public void setItem(ItemResponse item) {
        this.item = item;
    }
}
