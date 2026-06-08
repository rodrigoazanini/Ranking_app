package org.ranking_app.repository.item;

import org.ranking_app.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    @Query(value = "SELECT * FROM items WHERE name = :name", nativeQuery = true)
    Optional<Item> findByName(@Param("name") String name);
}
