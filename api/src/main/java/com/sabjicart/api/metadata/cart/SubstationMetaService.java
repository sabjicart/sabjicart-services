
package com.sabjicart.api.metadata.cart;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.metadata.cart.SubstationCartResponse;
import com.sabjicart.api.messages.metadata.cart.SubstationResponse;

public interface SubstationMetaService
{
    /**
     * Interface to fetch attached carts for a substation
     * @param substationId
     * @return
     */
    SubstationCartResponse getCarts (Long substationId) throws ServiceException;

    /**
     * Interface to fetch list of substations
     * @return
     * @throws ServiceException
     */
    SubstationResponse getSubstations () throws ServiceException;
}
