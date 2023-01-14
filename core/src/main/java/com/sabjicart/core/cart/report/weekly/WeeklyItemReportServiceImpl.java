package com.sabjicart.core.cart.report.weekly;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.common.CartItemInfoService;
import com.sabjicart.api.messages.common.ItemPojo;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.report.WeeklyItemReportResponse;
import com.sabjicart.api.model.CartItem;
import com.sabjicart.api.report.WeeklyItemReportPojo;
import com.sabjicart.api.report.weekly.WeeklyItemReportService;
import com.sabjicart.core.cart.repository.CartItemRepository;
import com.sabjicart.util.WeeklyItemReportConstant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@AllArgsConstructor
@Slf4j
public class WeeklyItemReportServiceImpl implements WeeklyItemReportService
{
    CartItemRepository cartItemRepository;
    CartItemInfoService itemPojoInfoService;

    @Override
    public WeeklyItemReportResponse getWeeklyItemReport (
        long substationId, LocalDate date) throws ServiceException
    {
        WeeklyItemReportResponse response = new WeeklyItemReportResponse();
        List<WeeklyItemReportPojo> weeklyItemReportPojoList = new ArrayList<>();
        List<CartItem> cartItems = new ArrayList<>();
        Map<String, WeeklyItemReportPojo> weeklyItemReportPojoHashMap =
            new HashMap<>();
        Map<String, Integer> countHashMap = new HashMap<>();
        List<ItemPojo> itemPojos;
        String itemId;

        try {
            for (int i = 0; i < WeeklyItemReportConstant.Week_Days; i++) {
                try {
                    cartItems.addAll(cartItemRepository.findAllByProcessingDateAndSubstationId(date.minusDays(i),
                        substationId
                    ));
                }
                catch (Exception e) {
                    log.error(
                        "Error fetching cart items for date={}, substationId={}",
                        date.plusDays(i),
                        substationId
                    );
                    throw new ServiceException("Error fetching cart items", e);
                }
            }
            itemPojos =
                itemPojoInfoService.cartItemToItemPojoConverter(cartItems);

            for (ItemPojo itemPojo : itemPojos) {

                itemId = itemPojo.getItemId();
                WeeklyItemReportPojo weeklyItemReportPojo;
                if (weeklyItemReportPojoHashMap.containsKey(itemId)) {
                    weeklyItemReportPojo =
                        weeklyItemReportPojoHashMap.get(itemId);
                    weeklyItemReportPojo.setAverageLastWeekSale(
                        weeklyItemReportPojo.getAverageLastWeekSale()
                            + itemPojo.getSaleValue());
                    weeklyItemReportPojoHashMap.replace(itemPojo.getItemId(),
                        weeklyItemReportPojo
                    );
                    countHashMap.replace(itemId, countHashMap.get(itemId) + 1);
                }
                else {
                    weeklyItemReportPojo = WeeklyItemReportPojo.builder()
                                                               .itemId(itemId)
                                                               .itemName(
                                                                   itemPojo.getItemName())
                                                               .denomination(
                                                                   itemPojo.getDenomination())
                                                               .averageLastWeekSale(
                                                                   itemPojo.getSaleValue())
                                                               .build();
                    weeklyItemReportPojoHashMap.put(itemId,
                        weeklyItemReportPojo
                    );
                    countHashMap.put(itemId, 1);
                }
            }
            for (WeeklyItemReportPojo weeklyItemReportPojo : weeklyItemReportPojoHashMap.values()) {
                weeklyItemReportPojo.setAverageLastWeekSale(
                    weeklyItemReportPojo.getAverageLastWeekSale()
                        / countHashMap.get(weeklyItemReportPojo.getItemId()));
                weeklyItemReportPojoList.add(weeklyItemReportPojo);
            }
            response.setWeeklyItemReportPojoList(weeklyItemReportPojoList);
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error(
                "Error fetching weekly item report for date={}, substationId={}",
                date,
                substationId
            );
            throw new ServiceException("Error fetching weekly item report", e);
        }
        ResponseUtil.success(response);
        return response;
    }

}
