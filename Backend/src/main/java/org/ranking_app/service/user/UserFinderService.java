package org.ranking_app.service.user;

import org.ranking_app.exception.user.UserNotFoundException;
import org.ranking_app.model.user.User;
import org.ranking_app.repository.user.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserFinderService {

    private final JpaUserRepository jpaUserRepository;

    public UserFinderService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public User find(Long id) {
        return jpaUserRepository.findById(id)
                .orElseThrow( () -> new UserNotFoundException(id));
    }
}
