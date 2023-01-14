
package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.Substation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubstationRepository extends JpaRepository<Substation, Long>
{
    /**
     * Get substation by name
     * @param substationName
     * @return
     */
    Substation findBySubstationName (
        @Param("substationName")
        String substationName);

    /**
     * Get active substations
     * @return
     */
    List<Substation> findByActiveTrue();
}
