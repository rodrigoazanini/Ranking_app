package org.ranking_app.repository.review;

import org.ranking_app.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    @Query("""
        SELECT
            MIN(r.price) AS priceMin,
            MAX(r.price) AS priceMax,
            AVG(r.ranking) AS rankingAvg
        FROM Review r
        WHERE r.item.id = :itemId
        AND r.date >= :cutoffDate
    """)
    ReviewItemStatsProjection findItemStatsByItemId(
            @Param("itemId") Long itemId,
            @Param("cutoffDate") LocalDate cutoffDate
    );

    @Query("""
        SELECT r
        FROM Review r
        WHERE r.user.id = :userId
        ORDER BY r.date DESC, r.id DESC
        """)
    List<Review> findReviewsByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT r
        FROM Review r
        WHERE r.item.id = :itemId
        ORDER BY r.date DESC, r.id DESC
        """)
    List<Review> findReviewsByItemId(@Param("itemId") Long itemId);

    @Query("""
        SELECT r
        FROM Review r
        WHERE r.user.id = :userId
        AND r.item.id = :itemId
        ORDER BY r.date DESC, r.id DESC
        """)
    List<Review> findReviewsByUserIdAndItemId(
            @Param("userId") Long userId,
            @Param("itemId") Long itemId
    );
}
