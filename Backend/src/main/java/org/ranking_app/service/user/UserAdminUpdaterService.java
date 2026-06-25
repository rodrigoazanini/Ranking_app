package org.ranking_app.service.user;

import jakarta.servlet.http.HttpServletRequest;
import org.ranking_app.dto.request.user.AdminRequest;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.user.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAdminUpdaterService {

    private final JpaUserRepository jpaUserRepository;
    private final UserFinderService userFinderService;

    public UserAdminUpdaterService(
            JpaUserRepository jpaUserRepository,
            UserFinderService userFinderService
    ) {
        this.jpaUserRepository = jpaUserRepository;
        this.userFinderService = userFinderService;
    }

    public User adminUpdate(AdminRequest adminRequest, Long id, HttpServletRequest httpRequest) {
        User authenticatedUser = userFinderService.findAuthenticatedUser(httpRequest);
        if (authenticatedUser.getAdmin()) {
            User user = userFinderService.find(id);

            if (adminRequest.getUserName() != null && !adminRequest.getUserName().isBlank()) {
                user.setUserName(adminRequest.getUserName());
            }

            if (adminRequest.getAdmin() != null) {
                user.setAdmin(adminRequest.getAdmin());
            }
            return jpaUserRepository.save(user);
        }

        throw new SecurityException("Acceso denegado");
    }
}
