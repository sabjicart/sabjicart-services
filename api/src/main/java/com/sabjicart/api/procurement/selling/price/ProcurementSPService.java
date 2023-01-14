
package com.sabjicart.api.procurement.selling.price;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.procurement.ProcurementSPRequest;

public interface ProcurementSPService
{
    /**
     * interface to update selling price of procured items
     * @param request
     * @return
     * @throws ServiceException
     */
    Response updateSellingPrice (ProcurementSPRequest request)
        throws ServiceException;
}
