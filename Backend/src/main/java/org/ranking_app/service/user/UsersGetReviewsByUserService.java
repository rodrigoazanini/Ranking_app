package org.ranking_app.service.user;

import org.ranking_app.model.review.Review;
import org.ranking_app.repository.user.JpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersGetReviewsByUserService {
    private final JpaUserRepository jpaUserRepository;

    public UsersGetReviewsByUserService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public List<Review> findReviewsByUserId(Long userId) {
        return jpaUserRepository.findReviewsByUserId(userId);
    }
}
