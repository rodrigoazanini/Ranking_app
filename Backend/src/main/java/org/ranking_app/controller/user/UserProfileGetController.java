package org.ranking_app.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.dto.response.user.UserProfileResponse;
import org.ranking_app.service.user.UserProfileFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users/profile")
public class UserProfileGetController {

    private final UserProfileFinderService userProfileFinderService;

    public UserProfileGetController(UserProfileFinderService userProfileFinderService) {
        this.userProfileFinderService = userProfileFinderService;
    }

    @GetMapping
    public ResponseEntity<UserProfileResponse> getUserProfile(HttpServletRequest httpRequest) {
        UserProfileResponse response = userProfileFinderService.findAuthenticatedUserProfile(httpRequest);
        return ResponseEntity.ok(response);
    }
}
