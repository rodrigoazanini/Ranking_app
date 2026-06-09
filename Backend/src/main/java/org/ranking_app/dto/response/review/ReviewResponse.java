package org.ranking_app.dto.response.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.ranking_app.model.review.Review;

import java.util.Date;

public class ReviewResponse {
    private Long id;
    private String comment;
    private Double ranking;
    private Double price;
    private Long itemId;
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date;

    public ReviewResponse() {}

    public ReviewResponse(
        Long id,
        String comment,
        Double ranking,
        Double price,
        Long itemId,
        Long userId,
        Date date
    ) {
        this.id = id;
        this.comment = comment;
        this.ranking = ranking;
        this.price = price;
        this.itemId = itemId;
        this.userId = userId;
        this.date = date;
    }

    static public ReviewResponse fromEntity(Review review) {
        return new ReviewResponse(
            review.getId(),
            review.getComment(),
            review.getRanking(),
            review.getPrice(),
            review.getItem() != null ? review.getItem().getId() : null,
            review.getUser() != null ? review.getUser().getId() : null,
            review.getDate()
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

    public Long getItemId() {
        return itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }
}
