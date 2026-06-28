package org.ranking_app.service.user;

import org.ranking_app.dto.response.user.TopUserReviewCountResponse;
import org.ranking_app.repository.user.JpaUserRepository;
import org.ranking_app.repository.user.TopUserReviewCountProjection;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersGetTopByReviewsService {
    private final JpaUserRepository jpaUserRepository;

    public UsersGetTopByReviewsService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public List<TopUserReviewCountResponse> findTopUsersByReviewCount(int quantity) {
        return jpaUserRepository.findTopUsersByReviewCount(PageRequest.of(0, Math.max(1, quantity)))
                .stream()
                .map(TopUserReviewCountResponse::fromProjection)
                .toList();
    }
}
