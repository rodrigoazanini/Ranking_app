package org.ranking_app.repository.user;

import org.ranking_app.model.review.Review;
import org.ranking_app.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query("""
        SELECT r
        FROM Review r
        WHERE r.user.id = :userId
        ORDER BY r.date DESC, r.id DESC
        """)
    List<Review> findReviewsByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT u
        FROM User u
        LEFT JOIN org.ranking_app.model.review.Review r ON r.user = u
        GROUP BY u
        ORDER BY COUNT(r.id) DESC, u.id DESC
        """)
    List<User> findTopUsersByReviewCount(Pageable pageable);
}
