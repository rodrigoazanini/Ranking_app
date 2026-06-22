package org.ranking_app.service.user;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.user.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDeleterService {
    private final JpaUserRepository jpaUserRepository;
    private final UserFinderService userFinderService;

    public UserDeleterService(
            JpaUserRepository jpaUserRepository,
            UserFinderService userFinderService
    ) {
        this.jpaUserRepository = jpaUserRepository;
        this.userFinderService = userFinderService;
    }

    public void delete(Long id, HttpServletRequest httpRequest) {
        User authenticatedUser = userFinderService.findAuthenticatedUser(httpRequest);

        if (!authenticatedUser.getAdmin()) {
            throw new SecurityException("Acceso denegado");
        }

        User user = userFinderService.find(id);
        jpaUserRepository.delete(user);
    }
}
