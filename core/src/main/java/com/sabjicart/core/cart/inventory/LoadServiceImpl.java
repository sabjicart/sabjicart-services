package com.sabjicart.core.cart.inventory;

import com.sabjicart.api.cart.inventory.LoadService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class LoadServiceImpl implements LoadService
{
    CartItemInfoService cartItemInfoService;

    CartItemRepository cartItemRepository;

    @Override
    public CartResponse fetchLoadedItems (
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
                                                            .itemValue(itemPojo.getLoadValue())
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
                                       .currentProcessStatus(CartStatus.LOAD)
                                       .build();
        }
        catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error(
                "Error while fetching loaded items for substation={} cart={} date={}",
                cartNumber,
                substationId,
                onDate
            );
            throw new ServiceException("Error while fetching loaded items", e);
        }
        ResponseUtil.success(cartResponse);
        return cartResponse;
    }

    @Override
    public Response updateLoadedItems (CartRequest cartLoadRequest)
        throws ServiceException
    {
        Response response = new Response();
        String cartNumber = cartLoadRequest.getCartNumber();
        LocalDate onDate = cartLoadRequest.getOnDate();
        long substationId = cartLoadRequest.getSubstationId();
        Map<String, CartItem> cartItemMap = new HashMap<>();
        Set<CartItem> itemsToDelete;
        List<CartItem> cartItemsList;

        //  Initialisation
        cartItemsList = new ArrayList<>();

        try {
            List<CartItem> cartItems =
                cartItemRepository.findAllByCartPlateNumberAndProcessingDateAndSubstationId(
                    cartNumber,
                    onDate,
                    substationId
                );

            // Delete existing items from cart which are no more available
            itemsToDelete = filterDeletedItems(cartItems,
                cartLoadRequest.getItemInfoList()
            );
            cartItemRepository.deleteAllInBatch(itemsToDelete);

            log.info(
                "Deleting {} items from cart={}, substation={}, onDate={}, items={}",
                itemsToDelete.size(),
                cartNumber,
                substationId,
                onDate,
                itemsToDelete
            );

            for (CartItem cartItem : cartItems) {
                cartItemMap.put(cartItem.getItemId(), cartItem);
            }

            for (ItemInfo itemUpdateInfo : cartLoadRequest.getItemInfoList()) {

                CartItem cartItem;
                if (cartItemMap.containsKey(itemUpdateInfo.getItemId())) {
                    cartItem = cartItemMap.get(itemUpdateInfo.getItemId());
                    cartItem.setQuantityLoad(itemUpdateInfo.getItemValue());
                    cartItem.setTimeLoaded(LocalDateTime.now());
                    cartItem.setLoadStatus(CartProgressStatus.COMPLETED);
                }
                else {
                    cartItem = CartItem.builder()
                                       .cartPlateNumber(cartNumber)
                                       .itemId(itemUpdateInfo.getItemId())
                                       .quantityLoad(itemUpdateInfo.getItemValue())
                                       .processingDate(onDate)
                                       .substationId(substationId)
                                       .timeLoaded(LocalDateTime.now())
                                       .loadStatus(CartProgressStatus.COMPLETED)
                                       .unloadStatus(CartProgressStatus.TODO)
                                       .saleStatus(CartProgressStatus.TODO)
                                       .build();

                    log.info(
                        "Adding new item from cart={}, substation={}, onDate={}, item={}",
                        itemsToDelete.size(),
                        cartNumber,
                        substationId,
                        onDate,
                        cartItem
                    );
                }

                cartItemsList.add(cartItem);
            }

            cartItemRepository.saveAll(cartItemsList);

            log.info(
                "Updating new items count={} from cart={}, substation={}, onDate={}",
                cartItemsList.size(),
                cartNumber,
                substationId,
                onDate
            );

        }
        catch (Exception e) {
            log.error(
                "Error while updating loaded items for cart={}, date={}, substation={}",
                cartNumber,
                onDate,
                substationId
            );
            throw new ServiceException("Error while updating loaded items", e);
        }
        ResponseUtil.success(response);
        return response;
    }

    // Method to get items in a cart which are no more available and need to be deleted from db
    private Set<CartItem> filterDeletedItems (
        List<CartItem> existingCartItems, List<ItemInfo> updatedItems)
    {

        // Declaration
        Set<CartItem> itemsToDelete;
        Set<String> updatedCartItemIdSet;

        // Initialisation
        itemsToDelete = new HashSet<>();
        updatedCartItemIdSet = new HashSet<>();

        for (ItemInfo itemInfo : updatedItems) {
            updatedCartItemIdSet.add(itemInfo.getItemId());
        }

        for (CartItem cartItem : existingCartItems) {

            if (!updatedCartItemIdSet.contains(cartItem.getItemId()))
                itemsToDelete.add(cartItem);
        }

        return itemsToDelete;
    }

}
