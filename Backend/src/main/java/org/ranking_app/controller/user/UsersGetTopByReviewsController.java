package org.ranking_app.controller.user;

import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.service.user.UsersGetTopByReviewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users/top/reviews")
public class UsersGetTopByReviewsController {
    private final UsersGetTopByReviewsService usersGetTopByReviewsService;

    public UsersGetTopByReviewsController(UsersGetTopByReviewsService usersGetTopByReviewsService) {
        this.usersGetTopByReviewsService = usersGetTopByReviewsService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getTopUsersByReviewCount(
            @RequestParam(defaultValue = "10") int quantity
    ) {
        List<UserResponse> response = usersGetTopByReviewsService.findTopUsersByReviewCount(quantity)
                .stream()
                .map(UserResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }
}
