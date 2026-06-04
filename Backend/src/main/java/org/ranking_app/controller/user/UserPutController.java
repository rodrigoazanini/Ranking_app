package org.ranking_app.controller.user;

import jakarta.validation.Valid;
import org.ranking_app.dto.request.user.UserRequest;
import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.user.User;
import org.ranking_app.service.user.UserUpdaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserPutController {
    private final UserUpdaterService userUpdaterService;

    public UserPutController(
            UserUpdaterService userUpdaterService
    ) {
        this.userUpdaterService = userUpdaterService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request
    ) {
        User user = userUpdaterService.update(request, id);

        UserResponse response = UserResponse.fromEntity(user);

        return ResponseEntity.ok(response);
    }
}
