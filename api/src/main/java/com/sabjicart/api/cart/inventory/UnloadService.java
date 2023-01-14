package com.sabjicart.api.cart.inventory;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.cart.inventory.CartRequest;
import com.sabjicart.api.messages.cart.inventory.CartResponse;
import com.sabjicart.api.messages.inoutbound.Response;

import java.time.LocalDate;

public interface UnloadService
{
    /**
     * Interface to fetch unloaded items
     * @param substation
     * @param cartNumber
     * @param onDate
     * @return
     * @throws ServiceException
     */
    CartResponse fetchUnloadedItems (
        long substation, String cartNumber, LocalDate onDate)
        throws ServiceException;

    /**
     * Interface to update unloaded items
     * @param cartUnloadRequest
     * @return
     * @throws ServiceException
     */
    Response updateUnloadedItems (CartRequest cartUnloadRequest)
        throws ServiceException;
}
