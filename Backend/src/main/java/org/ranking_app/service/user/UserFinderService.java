package org.ranking_app.service.user;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.exception.user.UserNotFoundException;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.user.JpaUserRepository;
import org.ranking_app.service.jwt.JwtService;
import org.springframework.stereotype.Service;

@Service
public class UserFinderService {

    private final JpaUserRepository jpaUserRepository;
    private final JwtService jwtService;

    public UserFinderService(JpaUserRepository jpaUserRepository, JwtService jwtService) {
        this.jpaUserRepository = jpaUserRepository;
        this.jwtService = jwtService;
    }

    public User find(Long id) {
        return jpaUserRepository.findById(id)
                .orElseThrow( () -> new UserNotFoundException(id));
    }

    public User findAuthenticatedUser(HttpServletRequest httpRequest) {
        Long userId = jwtService.extractUserId(httpRequest);
        return find(userId);
    }
}
