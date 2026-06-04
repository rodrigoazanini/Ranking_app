package org.ranking_app.controller.user;

import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.user.User;
import org.ranking_app.service.user.UserFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserGetController {

    private final UserFinderService userFinderService;

    public UserGetController(UserFinderService userFinderService) {
        this.userFinderService = userFinderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> find(@PathVariable Long id) {
       User user = userFinderService.find(id);

       UserResponse response = UserResponse.fromEntity(user);

       return ResponseEntity.ok(response);
    }
}
