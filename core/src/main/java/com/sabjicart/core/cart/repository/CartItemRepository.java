
package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.CartItem;
import com.sabjicart.api.shared.CartProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
    /**
     * Get cart items by cart plate number,processing date and substation id
     * @param cartPlateNumber
     * @param processingDate
     * @param substationId
     * @return
     */
    List<CartItem> findAllByCartPlateNumberAndProcessingDateAndSubstationId (
        @Param("cartPlateNumber")
        String cartPlateNumber,
        @Param("processingDate")
        LocalDate processingDate,
        @Param("substationId")
        long substationId);

    /**
     * Get cart items by cart plate number,processing date and substation id
     * @param cartPlateNumber
     * @param processingDate
     * @param substationId
     * @return
     */
    List<CartItem> findAllByCartPlateNumberAndProcessingDateAndSubstationIdAndLoadStatus (
        @Param("cartPlateNumber")
        String cartPlateNumber,
        @Param("processingDate")
        LocalDate processingDate,
        @Param("substationId")
        long substationId,
        @Param("loadStatus")
        CartProgressStatus loadStatus);

    /**
     * Get cart items by substation id,cart plate number,processing date and sale status
     * @param cartPlateNumber
     * @param processingDate
     * @param substationId
     * @param saleStatus
     * @return
     */
    List<CartItem> findAllBySubstationIdAndCartPlateNumberAndProcessingDateAndSaleStatus (
        @Param("substationId")
        long substationId,
        @Param("cartPlateNumber")
        String cartPlateNumber,
        @Param("processingDate")
        LocalDate processingDate,
        @Param("saleStatus")
        CartProgressStatus saleStatus);

    /**
     * Get cart items by processing date and substation id
     * @param processingDate
     * @param substationId
     * @return
     */
    List<CartItem> findAllByProcessingDateAndSubstationId (
        @Param("processingDate")
        LocalDate processingDate,
        @Param("substationId")
        long substationId);

    /**
     * Get cart items latest loaded date for a cart and substation id
     * @param substationId
     * @param cartPlateNumber
     * @return
     */
    @Query(
        "select max(ci.processingDate) from CartItem ci where ci.substationId =:substationId"
            + " and ci.cartPlateNumber =:cartPlateNumber and ci.loadStatus = 'COMPLETED'")
    LocalDate findLatestLoadedDateForCart (
        @Param("substationId")
        long substationId,
        @Param("cartPlateNumber")
        String cartPlateNumber);

    /**
     * Get cart items latest unloaded date for a cart and substation id
     * @param substationId
     * @param cartPlateNumber
     * @return
     */
    @Query(
        "select max(ci.processingDate) from CartItem ci where ci.substationId =:substationId"
            + " and ci.cartPlateNumber =:cartPlateNumber and ci.unloadStatus = 'COMPLETED'")
    LocalDate findLatestUnloadedDateForCart (
        @Param("substationId")
        long substationId,
        @Param("cartPlateNumber")
        String cartPlateNumber);

    /**
     * Get cart items latest sale data date for a cart and substation id
     * @param substationId
     * @param cartPlateNumber
     * @return
     */
    @Query(
        "select max(ci.processingDate) from CartItem ci where ci.substationId =:substationId"
            + " and ci.cartPlateNumber =:cartPlateNumber and ci.saleStatus = 'COMPLETED'")
    LocalDate findLatestSaleDataDateForCart (
        @Param("substationId")
        long substationId,
        @Param("cartPlateNumber")
        String cartPlateNumber);
}
