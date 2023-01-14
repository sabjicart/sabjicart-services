
package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCategoryRepository
    extends JpaRepository<TransactionCategory, Long>
{
    TransactionCategory findByCategoryName (String categoryName);
}
