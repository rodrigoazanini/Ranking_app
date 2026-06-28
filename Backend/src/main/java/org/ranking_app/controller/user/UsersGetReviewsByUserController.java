package org.ranking_app.controller.user;

import org.ranking_app.dto.response.review.ReviewResponse;
import org.ranking_app.service.user.UsersGetReviewsByUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UsersGetReviewsByUserController {
    private final UsersGetReviewsByUserService usersGetReviewsByUserService;

    public UsersGetReviewsByUserController(UsersGetReviewsByUserService usersGetReviewsByUserService) {
        this.usersGetReviewsByUserService = usersGetReviewsByUserService;
    }

    @GetMapping("/{userId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUser(@PathVariable Long userId) {
        List<ReviewResponse> response = usersGetReviewsByUserService.findReviewsByUserId(userId)
                .stream()
                .map(ReviewResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }
}
