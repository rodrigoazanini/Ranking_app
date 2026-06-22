package org.ranking_app.service.user;

import org.ranking_app.dto.request.user.AdminRequest;
import org.ranking_app.dto.request.user.UserRequest;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.user.JpaUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserUpdaterService {
    private final JpaUserRepository jpaUserRepository;
    private final UserFinderService userFinderService;
    private final PasswordEncoder passwordEncoder;

    public UserUpdaterService(
            JpaUserRepository jpaUserRepository,
            UserFinderService userFinderService,
            PasswordEncoder passwordEncoder) {
        this.jpaUserRepository = jpaUserRepository;
        this.userFinderService = userFinderService;
        this.passwordEncoder = passwordEncoder;
    }

    public User update(UserRequest userRequest, Long id) {
        User user = userFinderService.find(id);

        if (userRequest.getUserName() != null && !userRequest.getUserName().isBlank()) {
            user.setUserName(userRequest.getUserName());
        }
        if (userRequest.getEmail() != null && !userRequest.getEmail().isBlank()) {
            user.setEmail(userRequest.getEmail());
        }

        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        if (userRequest.getAdmin() != null) {
            user.setAdmin(userRequest.getAdmin());
        }

        return jpaUserRepository.save(user);
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
