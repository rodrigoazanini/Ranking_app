package org.ranking_app.exception.user_item_favorite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserItemFavoriteNotFoundException extends RuntimeException {

    public UserItemFavoriteNotFoundException(Long id) {
        super("User item favorite not found: Id " + id);
    }
}
