package org.ranking_app.service.user_item_favorite;

import org.ranking_app.model.item.Item;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.item.JpaItemRepository;
import org.ranking_app.repository.user.JpaUserRepository;
import org.ranking_app.exception.user.UserNotFoundException;
import org.ranking_app.exception.item.ItemNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FavoriteRemoveByItemService {

    private final JpaItemRepository itemRepository;
    private final JpaUserRepository userRepository;

    public FavoriteRemoveByItemService(
            JpaItemRepository itemRepository,
            JpaUserRepository userRepository
    ) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public void removeByUserIdAndItemId(Long userId, Long itemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        user.getFavorites().removeIf(fav -> fav.getItem().getId().equals(itemId));
        userRepository.save(user);
    }
}
