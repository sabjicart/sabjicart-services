package com.sabjicart.core.cart.inventory;

import com.sabjicart.api.cart.inventory.UnloadService;
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
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class UnloadServiceImpl implements UnloadService
{
    CartItemInfoService cartItemInfoService;
    CartItemRepository cartItemRepository;

    @Override
    public CartResponse fetchUnloadedItems (
        long substationId, String cartNumber, LocalDate onDate)
        throws ServiceException
    {
        CartResponse cartResponse;
        List<ItemInfo> itemInfoList = new ArrayList<>();
        try {
            List<ItemPojo> itemPojos = cartItemInfoService.getCartItemInfo(
                substationId,
                cartNumber,
                onDate
            );
            for (ItemPojo itemPojo : itemPojos) {
                try {
                    ItemInfo itemInfo = ItemInfo.builder()
                                                .itemId(itemPojo.getItemId())
                                                .itemValue(itemPojo.getUnloadValue())
                                                .build();
                    itemInfoList.add(itemInfo);
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
                                       .itemInfoList(itemInfoList)
                                       .onDate(onDate)
                                       .substationId(substationId)
                                       .currentProcessStatus(CartStatus.UNLOAD)
                                       .build();
        }
        catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error(
                "Error while fetching unloaded items for substationId={} cart={} date={}",
                cartNumber,
                substationId,
                onDate
            );
            throw new ServiceException("Error while fetching unloaded items",
                e
            );
        }
        ResponseUtil.success(cartResponse);
        return cartResponse;
    }

    @Override
    public Response updateUnloadedItems (CartRequest cartUnloadRequest)
        throws ServiceException
    {
        Response response = new Response();
        String cartNumber = cartUnloadRequest.getCartNumber();
        LocalDate onDate = cartUnloadRequest.getOnDate();
        long substationId = cartUnloadRequest.getSubstationId();
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
            for (ItemInfo itemInfo : cartUnloadRequest.getItemInfoList()) {
                if (cartItemMap.containsKey(itemInfo.getItemId())) {
                    CartItem cartItem =
                        cartItemMap.get(itemInfo.getItemId());
                    cartItem.setQuantityUnload(itemInfo.getItemValue());
                    cartItem.setTimeUnloaded(LocalDateTime.now());
                    cartItem.setUnloadStatus(CartProgressStatus.COMPLETED);
                    cartItemRepository.save(cartItem);
                }
                else {
                    CartItem newCartItem = CartItem.builder()
                                                   .cartPlateNumber(cartNumber)
                                                   .itemId(itemInfo.getItemId())
                                                   .quantityUnload(
                                                       itemInfo.getItemValue())
                                                   .processingDate(onDate)
                                                   .substationId(substationId)
                                                   .timeUnloaded(LocalDateTime.now())
                                                   .unloadStatus(
                                                       CartProgressStatus.COMPLETED)
                                                   .loadStatus(
                                                       CartProgressStatus.TODO)
                                                   .saleStatus(
                                                       CartProgressStatus.TODO)
                                                   .build();
                    cartItemRepository.save(newCartItem);
                }
            }
        }
        catch (Exception e) {
            log.error(
                "Error while updating unloaded items for cart={}, date={}, substation={}",
                cartNumber,
                onDate,
                substationId
            );
            throw new ServiceException("Error while updating unloaded items",
                e
            );
        }
        ResponseUtil.success(response);
        return response;
    }

}
