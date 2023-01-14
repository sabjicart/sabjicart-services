
package com.sabjicart.api.cart.inventory;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.cart.inventory.CartResponse;

public interface UnloadHelperService
{
    /**
     * Interface to inflate CartResponse with expected return value
     * @param cartResponse
     * @return
     */
    CartResponse cartResponseExpectedReturnInflater (CartResponse cartResponse)
        throws ServiceException;
}
