
package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.Item;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>
{
    /**
     * Get active items
     * @return
     */
    List<Item> findByActiveTrueOrderByItemNameAsc ();

    Item findByItemId(@Param("itemId")
                             String itemId);

    List<Item> findAllByOrderByItemNameAsc();
}
