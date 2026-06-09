package org.ranking_app.repository.user_item_favorite;

import org.ranking_app.model.user_item_favorite.UserItemFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserItemFavoriteRepository extends JpaRepository<UserItemFavorite, Long>, JpaSpecificationExecutor<UserItemFavorite> {
}
