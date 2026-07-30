package org.ranking_app.repository.item;

import org.ranking_app.model.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    @Query(value = "SELECT * FROM items WHERE name = :name", nativeQuery = true)
    Optional<Item> findByName(@Param("name") String name);

    @Query("""
        SELECT i
        FROM Item i
        WHERE i.enabled = true
        ORDER BY COALESCE(i.rankingAvg, 0) DESC, i.id DESC
        """)
    List<Item> findTopItemsByRanking(Pageable pageable);

    @Query("""
        SELECT i
        FROM Item i
        LEFT JOIN org.ranking_app.model.review.Review r ON r.item = i
        WHERE i.enabled = true
        GROUP BY i
        ORDER BY COUNT(r.id) DESC, COALESCE(i.rankingAvg, 0) DESC, i.id DESC
        """)
    List<Item> findTopItemsByReviewCount(Pageable pageable);

    @Query("""
        SELECT i
        FROM Item i
        WHERE (:pattern IS NULL OR LOWER(i.name) LIKE :pattern)
          AND (:brand IS NULL OR LOWER(i.brand) LIKE LOWER(:brand))
          AND (:suggested IS NULL OR i.suggested = :suggested)
          AND (:enabled IS NULL OR i.enabled = :enabled)
          AND (:category IS NULL OR LOWER(i.category.name) LIKE LOWER(:category))
        """)
    Page<Item> findByFilters(
            @Param("pattern") String pattern,
            @Param("brand") String brand,
            @Param("suggested") Boolean suggested,
            @Param("enabled") Boolean enabled,
            @Param("category") String category,
            Pageable pageable
    );

    @Query("""
        SELECT i
        FROM Item i
        WHERE i.enabled = true
        ORDER BY i.id DESC
        """)
    Page<Item> findLatestEnabledItems(Pageable pageable);
}
