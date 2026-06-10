package org.ranking_app.dto.response.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.ranking_app.dto.response.item.ItemResponse;
import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.review.Review;

import java.util.Date;

public class ReviewResponse {
    private Long id;
    private String comment;
    private Double ranking;
    private Double price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date;

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
        this.id = id;
        this.comment = comment;
        this.ranking = ranking;
        this.price = price;
        this.date = date;
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

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Double getRanking() {
        return ranking;
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public ItemResponse getItemResponse() {
        return itemResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }
}