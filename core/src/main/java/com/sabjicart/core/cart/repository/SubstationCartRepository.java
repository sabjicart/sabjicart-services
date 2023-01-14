
package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.SubstationCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubstationCartRepository
    extends JpaRepository<SubstationCart, Long>
{
    /**
     *
     * @param substationId
     * @return
     */

    List<SubstationCart> findBySubstationId (
        @Param("substationId")
        Long substationId);
    List<SubstationCart> findBySubstationIdAndActiveTrue (
        @Param("substationId")
        Long substationId);
}
