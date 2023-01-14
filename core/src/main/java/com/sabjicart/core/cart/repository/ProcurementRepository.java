
package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, Long>
{
    /**
     * Get procurement for a substation id and procurement date
     * @param substationId
     * @param procurementDate
     * @return
     */
    List<Procurement> findAllBySubstationIdAndProcurementDate (
        @Param("substationId")
        String substationId,
        @Param("procurementDate")
        LocalDate procurementDate);
}

