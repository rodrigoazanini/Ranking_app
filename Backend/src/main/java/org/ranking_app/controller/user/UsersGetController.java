package org.ranking_app.controller.user;

import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.user.User;
import org.ranking_app.service.user.UsersSearcherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UsersGetController {
    private final UsersSearcherService usersSearcherService;

    public UsersGetController(
            UsersSearcherService usersSearcherService
    ) {
        this.usersSearcherService = usersSearcherService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = usersSearcherService.findAll(pageable);

        return ResponseEntity.ok(
                users.map(
                        user -> UserResponse.fromEntity(user)
                )
        );
    }
}
