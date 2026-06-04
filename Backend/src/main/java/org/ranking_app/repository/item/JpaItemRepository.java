package org.ranking_app.repository.item;

import org.ranking_app.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
}
