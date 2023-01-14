package com.sabjicart.core.cart.inventory;

import com.sabjicart.api.cart.inventory.SaleService;
import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.cart.inventory.CartRequest;
import com.sabjicart.api.messages.cart.inventory.CartResponse;
import com.sabjicart.api.messages.common.CartItemInfoService;
import com.sabjicart.api.messages.common.ItemPojo;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.model.CartItem;
import com.sabjicart.api.shared.CartProgressStatus;
import com.sabjicart.api.shared.CartStatus;
import com.sabjicart.api.shared.ItemInfo;
import com.sabjicart.api.shared.UnloadItemInfo;
import com.sabjicart.core.cart.repository.CartItemRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class SaleServiceImpl implements SaleService
{
    CartItemInfoService cartItemInfoService;

    CartItemRepository cartItemRepository;

    @Override
    public CartResponse fetchSaleData (
        long substationId, String cartNumber, LocalDate onDate)
        throws ServiceException
    {
        CartResponse cartResponse;
        List<UnloadItemInfo> unloadItemInfos = new ArrayList<>();
        try {
            List<ItemPojo> itemPojos = cartItemInfoService.getCartItemInfo(
                substationId,
                cartNumber,
                onDate
            );
            for (ItemPojo itemPojo : itemPojos) {
                try {
                    UnloadItemInfo itemInfo = UnloadItemInfo.builder()
                                                            .itemId(itemPojo.getItemId())
                                                            .itemValue(itemPojo.getSaleValue())
                                                            .build();
                    unloadItemInfos.add(itemInfo);
                }
                catch (Exception e) {
                    log.error("Error while fetching item info for item {}",
                        itemPojo.getItemId()
                    );
                    throw new ServiceException("Error while fetching item info");
                }
            }
            cartResponse = CartResponse.builder()
                                       .cartPlateNumber(cartNumber)
                                       .unloadItemInfoList(unloadItemInfos)
                                       .onDate(onDate)
                                       .substationId(substationId)
                                       .currentProcessStatus(CartStatus.SALEDATA)
                                       .build();
        }
        catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error(
                "Error while fetching sale data for substationId={} cart={} date={}",
                cartNumber,
                substationId,
                onDate
            );
            throw new ServiceException("Error while fetching sale data", e);
        }
        ResponseUtil.success(cartResponse);
        return cartResponse;
    }

    @Override
    public Response updateSaleData (CartRequest cartSaleDataRequest)
        throws ServiceException
    {
        Response response = new Response();
        String cartNumber = cartSaleDataRequest.getCartNumber();
        LocalDate onDate = cartSaleDataRequest.getOnDate();
        long substationId = cartSaleDataRequest.getSubstationId();
        Map<String, CartItem> cartItemMap = new HashMap<>();
        try {
            List<CartItem> cartItems =
                cartItemRepository.findAllByCartPlateNumberAndProcessingDateAndSubstationId(
                    cartNumber,
                    onDate,
                    substationId
                );
            for (CartItem cartItem : cartItems) {
                cartItemMap.put(cartItem.getItemId(), cartItem);
            }
            for (ItemInfo itemUpdateInfo : cartSaleDataRequest.getItemInfoList()) {
                if (cartItemMap.containsKey(itemUpdateInfo.getItemId())) {
                    CartItem cartItem =
                        cartItemMap.get(itemUpdateInfo.getItemId());
                    cartItem.setQuantitySale(itemUpdateInfo.getItemValue());
                    cartItem.setTimeSaleUpdated(LocalDateTime.now());
                    cartItem.setSaleStatus(CartProgressStatus.COMPLETED);
                    cartItemRepository.save(cartItem);
                }
                else {
                    CartItem newCartItem = CartItem.builder()
                                                   .cartPlateNumber(cartNumber)
                                                   .itemId(itemUpdateInfo.getItemId())
                                                   .quantitySale(itemUpdateInfo.getItemValue())
                                                   .processingDate(onDate)
                                                   .quantitySale(itemUpdateInfo.getItemValue())
                                                   .processingDate(onDate) // use zone id to-do
                                                   .substationId(substationId)
                                                   .timeSaleUpdated(
                                                       LocalDateTime.now())
                                                   .saleStatus(
                                                       CartProgressStatus.COMPLETED)
                                                   .loadStatus(
                                                       CartProgressStatus.TODO)
                                                   .unloadStatus(
                                                       CartProgressStatus.TODO)
                                                   .build();
                    cartItemRepository.save(newCartItem);
                }
            }
        }
        catch (Exception e) {
            log.error(
                "Error while updating sale data for cart={}, date={}, substation={}",
                cartNumber,
                onDate,
                substationId
            );
            throw new ServiceException("Error while updating sale data", e);
        }
        ResponseUtil.success(response);
        return response;
    }
}
