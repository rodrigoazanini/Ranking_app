package org.ranking_app.dto.response.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.review.Review;

import java.util.Date;

public class ReviewResponse extends ReviewSummaryResponse{

    private ItemResponse itemResponse;
    private UserResponse userResponse;

    public ReviewResponse() {}

    public ReviewResponse(
        Long id,
        String comment,
        Double ranking,
        Double price,
        Date date,
        ItemResponse itemResponse,
        UserResponse userResponse

    ) {
        super (id, comment, ranking, price, date);
        this.itemResponse = itemResponse;
        this.userResponse = userResponse;
    }

    static public ReviewResponse fromEntity(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getComment(),
                review.getRanking(),
                review.getPrice(),
                review.getDate(),
                ItemResponse.fromEntity(review.getItem()),
                UserResponse.fromEntity(review.getUser())
        );
    }

    public ItemResponse getItemResponse() {
        return itemResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }
}