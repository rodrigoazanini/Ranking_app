package org.ranking_app.service.user;

import org.ranking_app.model.user.User;
import org.ranking_app.repository.user.JpaUserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersGetTopByReviewsService {
    private final JpaUserRepository jpaUserRepository;

    public UsersGetTopByReviewsService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public List<User> findTopUsersByReviewCount(int quantity) {
        int safeQuantity = Math.max(1, quantity);
        return jpaUserRepository.findTopUsersByReviewCount(PageRequest.of(0, safeQuantity));
    }
}
