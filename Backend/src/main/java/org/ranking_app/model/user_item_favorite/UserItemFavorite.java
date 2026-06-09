package org.ranking_app.model.user_item_favorite;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;

@Entity
@Table(
    name = "user_item_favorites",
    uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "item_id"})
)
public class UserItemFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "item_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_item_favorites_item")
    )
    @NotNull
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "userId",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_item_favorites_user")// no se crea?
    )
    @NotNull
    private User user;

    public UserItemFavorite() {}

    public UserItemFavorite(
        Long id,
        Item item,
        User user
    ) {
        this.id = id;
        this.item = item;
        this.user = user;
    }

    static public UserItemFavorite fromRequest(Item item, User user) {
        return new UserItemFavorite(
            null,
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
