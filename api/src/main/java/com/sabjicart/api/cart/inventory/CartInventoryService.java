
package com.sabjicart.api.cart.inventory;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.cart.inventory.CartRequest;
import com.sabjicart.api.messages.cart.inventory.CartResponse;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.shared.CartStatus;

import java.time.LocalDate;

public interface CartInventoryService {
    /**
     * Interface to get data for a cart on a specific date
     *
     * @param substation
     * @param cartNumber
     * @param onDate
     * @param status
     * @return
     */
    CartResponse getCartItemsAsOfDate(
        long substation, String cartNumber, LocalDate onDate, CartStatus status)
            throws ServiceException;

    /**
     * Interface to add/update items to a cart
     *
     * @param cartLoadRequest
     * @param status
     * @return
     */
    Response updateCartItems(CartRequest cartLoadRequest, CartStatus status)
            throws ServiceException;

}
