
package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository
    extends JpaRepository<com.sabjicart.api.model.Balance, Long>
{
    /**
     * interface to get latest Balance data
     * @return
     */
    Balance findTop1ByOrderByDateDesc ();
}
