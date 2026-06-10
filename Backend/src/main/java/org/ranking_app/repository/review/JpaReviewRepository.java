package org.ranking_app.repository.review;

import org.ranking_app.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface JpaReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    @Query("""
    SELECT
        MIN(CASE WHEN r.date < :cutoffDate THEN r.price END),
        MAX(CASE WHEN r.date < :cutoffDate THEN r.price END),
        AVG(r.ranking)
    FROM Review r
    WHERE r.item.id = :itemId
""")
    ReviewItemStatsProjection findItemStatsByItemId(
            @Param("itemId") Long itemId,
            @Param("cutoffDate") LocalDate cutoffDate
    );
}
