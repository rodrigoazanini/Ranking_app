package org.ranking_app.service.user;

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

    public void delete(Long id) {
        User user = userFinderService.find(id);
        jpaUserRepository.delete(user);
    }
}
