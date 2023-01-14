package com.sabjicart.api.report.weekly;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.report.WeeklyItemReportResponse;

import java.time.LocalDate;

public interface WeeklyItemReportService
{
    /**
     * Interface to get weekly average sale value report
     * @param substationId
     * @param date
     * @return
     * @throws ServiceException
     */
    WeeklyItemReportResponse getWeeklyItemReport (
        long substationId, LocalDate date) throws ServiceException;
}
