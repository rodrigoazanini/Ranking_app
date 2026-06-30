package org.ranking_app.service.user;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.dto.response.user.UserProfileResponse;
import org.ranking_app.model.user.User;
import org.ranking_app.service.jwt.JwtService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileFinderService {

    private final UserFinderService userFinderService;
    private final JwtService jwtService;

    public UserProfileFinderService(UserFinderService userFinderService, JwtService jwtService) {
        this.userFinderService = userFinderService;
        this.jwtService = jwtService;
    }

    public UserProfileResponse findAuthenticatedUserProfile(HttpServletRequest httpRequest) {
        User user = userFinderService.findAuthenticatedUser(httpRequest);
        return UserProfileResponse.fromEntity(user);
    }

    public UserProfileResponse findUserProfile(Long userId) {
        User user = userFinderService.find(userId);
        return UserProfileResponse.fromEntity(user);
    }
}
