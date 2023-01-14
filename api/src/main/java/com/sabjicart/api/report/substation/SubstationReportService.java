
package com.sabjicart.api.report.substation;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.report.ReportResponse;

import java.time.LocalDate;

public interface SubstationReportService
{

    /**
     * Interface to get substation report
     * @param substationId
     * @param date
     * @return
     */
    ReportResponse getSubstationReport (long substationId, LocalDate date)
        throws ServiceException;

}
