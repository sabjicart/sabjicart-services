
package com.sabjicart.core.cart.report.substation;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.common.AnomalyDetectionService;
import com.sabjicart.api.messages.common.CartItemInfoService;
import com.sabjicart.api.messages.common.ItemPojo;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.report.ReportResponse;
import com.sabjicart.api.report.ReportItemPojo;
import com.sabjicart.api.report.substation.SubstationReportService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@AllArgsConstructor
@Slf4j
public class SubstationReportServiceImpl implements SubstationReportService
{

    AnomalyDetectionService anomalyDetectionService;

    CartItemInfoService itemPojoInfoService;

    @Override
    public ReportResponse getSubstationReport (
        long substationId, LocalDate date) throws ServiceException
    {
        ReportResponse reportResponse = new ReportResponse();
        List<ReportItemPojo> reportItemPojos = new ArrayList<>();
        try {
            List<ItemPojo> itemPojos =
                itemPojoInfoService.getCartItemInfo(substationId, date);

            for (ItemPojo itemPojo : itemPojos) {
                try {
                    ReportItemPojo reportItemPojo = ReportItemPojo.builder()
                                                                  .denomination(
                                                                      itemPojo.getDenomination())
                                                                  .saleValue(
                                                                      itemPojo.getSaleValue())
                                                                  .loadValue(
                                                                      itemPojo.getLoadValue())
                                                                  .unloadValue(
                                                                      itemPojo.getUnloadValue())
                                                                  .itemName(
                                                                      itemPojo.getItemName())
                                                                  .itemId(
                                                                      itemPojo.getItemId())
                                                                  .build();
                    reportItemPojos.add(reportItemPojo);
                }
                catch (Exception e) {
                    log.error("Error while getting item details for item id: " + itemPojo.getItemId(),
                        e
                    );
                    throw e;
                }
            }
            anomalyDetectionService.itemPojoAnomalyInflater(reportItemPojos);
            reportResponse.setItems(reportItemPojos);
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error(
                "Error fetching substation report for substationId={}, date={}",
                substationId,
                date
            );
            throw new ServiceException("Error fetching substation report", e);
        }

        ResponseUtil.success(reportResponse);
        return reportResponse;
    }
}
