
package com.sabjicart.api.messages.common;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.report.ReportCartItemPojo;
import com.sabjicart.api.report.ReportItemPojo;

import java.util.List;

public interface AnomalyDetectionService
{

    /**
     * Interface to detect anomaly deviation
     * @param load quantity of item loaded in a specific denomination
     * @param unload quantity of item un-loaded in a specific denomination
     * @param sale quantity of item sold in a specific denomination
     * @return
     */
    double calculateAnomaly (double load, double unload, double sale);

    /**
     * Interface to populate anomaly deviation for list of items
     * @param reportItemPojos
     * @return
     */
    List<ReportItemPojo> itemPojoAnomalyInflater (List<ReportItemPojo> reportItemPojos)
        throws ServiceException;

    List<ReportCartItemPojo> cartItemPojoAnomalyInflater (List<ReportCartItemPojo> reportCartItemPojos)
        throws ServiceException;

}
