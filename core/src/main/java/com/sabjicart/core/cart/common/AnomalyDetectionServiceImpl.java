package com.sabjicart.core.cart.common;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.common.AnomalyDetectionService;
import com.sabjicart.api.report.ReportCartItemPojo;
import com.sabjicart.api.report.ReportItemPojo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class AnomalyDetectionServiceImpl implements AnomalyDetectionService
{
    @Override
    public double calculateAnomaly (double load, double unload, double sale)
    {
        return load - (unload + sale);
    }

    @Override
    public List<ReportItemPojo> itemPojoAnomalyInflater (List<ReportItemPojo> reportItemPojos)
        throws ServiceException

    {
        for (ReportItemPojo reportItemPojo : reportItemPojos) {
            try {
                double anomaly = calculateAnomaly(
                    reportItemPojo.getLoadValue(),
                    reportItemPojo.getUnloadValue(),
                    reportItemPojo.getSaleValue()
                );
                reportItemPojo.setAnomaly(anomaly); // concurrent modification exception
            }
            catch (Exception e) {
                log.error(
                    "Error evaluating anomaly for item: " + reportItemPojo);
                throw new ServiceException("Error evaluating anomaly", e);
            }
        }

        return reportItemPojos;
    }

    @Override
    public List<ReportCartItemPojo> cartItemPojoAnomalyInflater(List<ReportCartItemPojo> reportCartItemPojos) throws ServiceException {
        for (ReportCartItemPojo reportCartItemPojo : reportCartItemPojos) {
            try {
                double anomaly = calculateAnomaly(
                    reportCartItemPojo.getLoadValue(),
                    reportCartItemPojo.getUnloadValue(),
                    reportCartItemPojo.getSaleValue()
                );
                reportCartItemPojo.setAnomaly(anomaly); // concurrent modification exception
            }
            catch (Exception e) {
                log.error(
                    "Error evaluating anomaly for item: " + reportCartItemPojo);
                throw new ServiceException("Error evaluating anomaly", e);
            }
        }
        return reportCartItemPojos;
    }
}
