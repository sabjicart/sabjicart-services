
package com.sabjicart.api.procurement;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.procurement.ProcurementRequest;
import com.sabjicart.api.messages.procurement.ProcurementResponse;

import java.time.LocalDate;

public interface ProcurementService
{
    /**
     * interface to update procured items
     * @param request
     * @return
     * @throws ServiceException
     */
    Response updateProcurement (ProcurementRequest request)
        throws ServiceException;

    /**
     * interface to get procured items
     * @param substationId
     * @param date
     * @return
     * @throws ServiceException
     */
    ProcurementResponse getProcurement (String substationId, LocalDate date)
        throws ServiceException;
}
