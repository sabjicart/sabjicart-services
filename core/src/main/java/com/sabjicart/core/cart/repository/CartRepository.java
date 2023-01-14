
package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>
{
    /**
     * Get cart by cart plate number
     * @param cartPlateNumber
     * @return
     */
    Cart findByCartPlateNumber (String cartPlateNumber);
}
