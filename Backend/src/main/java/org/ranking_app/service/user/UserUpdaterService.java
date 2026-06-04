package org.ranking_app.service.user;

import org.ranking_app.dto.request.user.UserRequest;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.user.JpaUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserUpdaterService {
    private final JpaUserRepository jpaUserRepository;
    private final UserFinderService userFinderService;
    private final PasswordEncoder passwordEncoder;

    public UserUpdaterService(
            JpaUserRepository jpaUserRepository,
            UserFinderService userFinderService,
            PasswordEncoder passwordEncoder
    ) {
        this.jpaUserRepository = jpaUserRepository;
        this.userFinderService = userFinderService;
        this.passwordEncoder = passwordEncoder;
    }

    public User update(UserRequest userRequest, Long id) {
        User user = userFinderService.find(id);

        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setDni(userRequest.getDni());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        return jpaUserRepository.save(user);
    }
}
