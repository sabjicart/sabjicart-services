package com.sabjicart.api.cart.inventory;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.cart.inventory.CartRequest;
import com.sabjicart.api.messages.cart.inventory.CartResponse;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.shared.CartStatus;

import java.time.LocalDate;

public interface LoadService
{
    /**
     * Interface to fetch loaded items
     * @param substation
     * @param cartNumber
     * @param onDate
     * @return
     * @throws ServiceException
     */
    CartResponse fetchLoadedItems (
        long substation, String cartNumber, LocalDate onDate)
        throws ServiceException;

    /**
     * Interface to update loaded items
     * @param cartLoadRequest
     * @return
     * @throws ServiceException
     */
     Response updateLoadedItems (CartRequest cartLoadRequest) throws ServiceException;
}
