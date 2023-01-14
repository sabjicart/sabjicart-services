
package com.sabjicart.api.report.cart;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.report.ReportResponse;

import java.time.LocalDate;

public interface CartReportService
{

    /**
     * Interface to get cart report
     * @param substationId
     * @param cartPlateNumber
     * @param date
     * @return
     */
    ReportResponse getCartReport (
        long substationId,
        String cartPlateNumber,
        LocalDate date) throws ServiceException;
}
