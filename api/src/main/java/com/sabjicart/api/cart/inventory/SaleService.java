package com.sabjicart.api.cart.inventory;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.cart.inventory.CartRequest;
import com.sabjicart.api.messages.cart.inventory.CartResponse;
import com.sabjicart.api.messages.inoutbound.Response;

import java.time.LocalDate;

public interface SaleService
{
    /**
     * Interface to fetch sold items
     * @param substation
     * @param cartNumber
     * @param onDate
     * @return
     * @throws ServiceException
     */
    CartResponse fetchSaleData (
        long substation, String cartNumber, LocalDate onDate)
        throws ServiceException;

    /**
     * Interface to update sold items
     * @param cartSaleDataRequest
     * @return
     * @throws ServiceException
     */
    Response updateSaleData (CartRequest cartSaleDataRequest)
        throws ServiceException;
}
