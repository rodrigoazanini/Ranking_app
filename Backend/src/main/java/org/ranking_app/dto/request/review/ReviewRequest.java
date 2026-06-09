package org.ranking_app.dto.request.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class ReviewRequest {

    @NotBlank(message = "El comentario es obligatorio")
    private String comment;

    @NotNull(message = "El ranking es obligatorio")
    private Double ranking;

    @NotNull(message = "El precio es obligatorio")
    private Double price;

    @NotNull(message = "El item es obligatorio")
    private Long itemId;

    @NotNull(message = "El usuario es obligatorio")
    private Long userId;

    @NotNull(message = "La fecha es obligatoria")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date;

    public ReviewRequest() {}

    public ReviewRequest(
        String comment,
        Double ranking,
        Double price,
        Long itemId,
        Long userId,
        Date date
    ) {
        this.comment = comment;
        this.ranking = ranking;
        this.price = price;
        this.itemId = itemId;
        this.userId = userId;
        this.date = date;
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
