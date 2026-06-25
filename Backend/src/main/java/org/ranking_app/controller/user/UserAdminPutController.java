package org.ranking_app.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ranking_app.dto.request.user.AdminRequest;
import org.ranking_app.dto.response.user.UserResponse;
import org.ranking_app.model.user.User;
import org.ranking_app.service.user.UserAdminUpdaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/users")
public class UserAdminPutController {
    private final UserAdminUpdaterService userAdminUpdaterService;

    public UserAdminPutController(
            UserAdminUpdaterService userAdminUpdaterService
    ) {
        this.userAdminUpdaterService = userAdminUpdaterService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AdminRequest request,
            HttpServletRequest httpRequest
    ) {
        User user = userAdminUpdaterService.adminUpdate(request, id, httpRequest);
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }
}
