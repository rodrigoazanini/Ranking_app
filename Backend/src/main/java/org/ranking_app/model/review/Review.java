package org.ranking_app.model.review;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ranking_app.dto.request.review.ReviewRequest;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;

import java.util.Date;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String comment;

    @Column(nullable = false)
    private Double ranking;

    @Column(nullable = false)
    private Double price;

    @Column(name = "review_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "item_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_item")
    )
    @NotNull
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_user")
    )
    @NotNull
    private User user;

    public Review() {}

    public Review(
        Long id,
        String comment,
        Double ranking,
        Double price,
        Date date,
        Item item,
        User user
    ) {
        this.id = id;
        this.comment = comment;
        this.ranking = ranking;
        this.price = price;
        this.date = date;
        this.item = item;
        this.user = user;
    }

    static public Review fromRequest(ReviewRequest request, Item item, User user) {
        return new Review(
            null,
            request.getComment(),
            request.getRanking(),
            request.getPrice(),
            request.getDate(),
            item,
            user
        );
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRanking() {
        return ranking;
    }
    public void setRanking(Double ranking) {
        this.ranking = ranking;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}