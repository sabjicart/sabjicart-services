
package com.sabjicart.core.cart.report.cart;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.common.AnomalyDetectionService;
import com.sabjicart.api.messages.common.CartItemInfoService;
import com.sabjicart.api.messages.common.ItemPojo;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.report.ReportItemResponse;
import com.sabjicart.api.messages.report.ReportResponse;
import com.sabjicart.api.model.CartItem;
import com.sabjicart.api.report.ReportCartItemPojo;
import com.sabjicart.api.report.ReportItemPojo;
import com.sabjicart.api.report.cart.CartReportService;
import com.sabjicart.core.cart.repository.CartItemRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class CartReportServiceImpl implements CartReportService
{

    CartItemInfoService itemPojoInfoService;

    AnomalyDetectionService anomalyDetectionService;

    CartItemRepository cartItemRepository;


    @Override
    public ReportResponse getCartReport (
        long substationId, String cartPlateNumber, LocalDate date)
        throws ServiceException
    {

        ReportResponse reportResponse = new ReportResponse();
        List<ReportItemPojo> reportItemPojos = new ArrayList<>();
        try {
            List<ItemPojo> itemPojos = itemPojoInfoService.getCartItemInfo(
                substationId,
                cartPlateNumber,
                date
            );

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
                "Error fetching cart report for substationId={}, cartPlateNumber={}, date={}",
                substationId,
                cartPlateNumber,
                date
            );
            throw new ServiceException("Error fetching cart report", e);
        }

        ResponseUtil.success(reportResponse);
        return reportResponse;
    }

    @Override
    public ReportItemResponse getCartItemReport(String cartPlateNumber, String itemId) throws ServiceException {
        ReportItemResponse reportItemResponse = new ReportItemResponse();
        List<ReportCartItemPojo> reportCartItemPojos = new ArrayList<>();
        try {
            List<ItemPojo> itemPojos = itemPojoInfoService.getCartItemInfo(cartPlateNumber, itemId);
            for (ItemPojo itemPojo : itemPojos) {
                ReportCartItemPojo reportItemPojo = ReportCartItemPojo.builder()
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
                        .date(itemPojo.getDate())
                        .build();
                reportCartItemPojos.add(reportItemPojo);
            }
            anomalyDetectionService.cartItemPojoAnomalyInflater(reportCartItemPojos);
            reportItemResponse.setItems(reportCartItemPojos);
        } catch (Exception e) {
            log.error(
                "Error fetching cart report for  cartPlateNumber={}, date={}",
                cartPlateNumber,
                itemId
            );
            throw new ServiceException("Error fetching cart report", e);
        }

        ResponseUtil.success(reportItemResponse);
        return reportItemResponse;
    }
}
