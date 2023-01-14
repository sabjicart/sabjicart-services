package com.sabjicart.core.cart.common;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.common.CartItemInfoService;
import com.sabjicart.api.messages.common.ItemPojo;
import com.sabjicart.api.model.CartItem;
import com.sabjicart.api.model.Item;
import com.sabjicart.api.shared.CartProgressStatus;
import com.sabjicart.api.shared.CartStatus;
import com.sabjicart.core.cart.repository.CartItemRepository;
import com.sabjicart.core.cart.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class CartItemInfoServiceImpl implements CartItemInfoService
{
    CartItemRepository cartItemRepository;

    ItemRepository itemRepository;

    @Override
    public List<ItemPojo> getCartItemInfo (
        long substationId, String cartPlateNumber, LocalDate date)
        throws ServiceException
    {
        List<ItemPojo> itemPojos;
        try {
            List<CartItem> cartItems =
                cartItemRepository.findAllByCartPlateNumberAndProcessingDateAndSubstationIdAndLoadStatus(
                    cartPlateNumber,
                    date,
                    substationId,
                    CartProgressStatus.COMPLETED
                );

            itemPojos = cartItemToItemPojoConverter(cartItems);
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error(
                "Error fetching cart items for cartPlateNumber={}, date={}, substationId={}",
                cartPlateNumber,
                date,
                substationId
            );
            throw new ServiceException("Error fetching cart items", e);
        }
        return itemPojos;
    }

    @Override
    public List<ItemPojo> getCartItemInfo (long substationId, LocalDate date)
        throws ServiceException
    {
        List<ItemPojo> itemPojos;
        HashMap<String, ItemPojo> itemPojoHashMap;
        try {
            List<CartItem> cartItems =
                cartItemRepository.findAllByProcessingDateAndSubstationId(date,
                    substationId
                );
            itemPojos = cartItemToItemPojoConverter(cartItems);
            itemPojoHashMap = itemPojosAdder(itemPojos);
            itemPojos = new ArrayList<>(itemPojoHashMap.values());
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error("Error fetching cart items for date={}, substationId={}",
                date,
                substationId
            );
            throw new ServiceException("Error fetching cart items", e);
        }
        return itemPojos;
    }

    @Override
    public HashMap<String, ItemPojo> itemPojosAdder (List<ItemPojo> itemPojos)
        throws ServiceException
    {
        HashMap<String, ItemPojo> itemPojoHashMap = new HashMap<>();
        try {
            for (ItemPojo itemPojo : itemPojos) {
                if (itemPojoHashMap.containsKey(itemPojo.getItemId())) {
                    ItemPojo itemPojoNew =
                        itemPojoHashMap.get(itemPojo.getItemId());
                    itemPojoNew.setSaleValue(
                        itemPojoNew.getSaleValue() + itemPojo.getSaleValue());
                    itemPojoNew.setLoadValue(
                        itemPojoNew.getLoadValue() + itemPojo.getLoadValue());
                    itemPojoNew.setUnloadValue(itemPojoNew.getUnloadValue()
                        + itemPojo.getUnloadValue());
                    itemPojoHashMap.replace(itemPojo.getItemId(), itemPojoNew);
                }
                else {
                    itemPojoHashMap.put(itemPojo.getItemId(), itemPojo);
                }
            }
        }
        catch (Exception e) {
            log.error("Error adding item pojos data");
            throw new ServiceException("Error adding item pojos data", e);
        }
        return itemPojoHashMap;
    }

    @Override
    public List<ItemPojo> cartItemToItemPojoConverter (List<CartItem> cartItems)
        throws ServiceException
    {
        List<ItemPojo> itemPojos = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            try {
                Item item = itemRepository.findByItemId(cartItem.getItemId());

                if (null == item)
                    throw new ServiceException(
                        "Item not found for id: " + cartItem.getItemId());
                ItemPojo itemPojo = ItemPojo.builder()
                                            .denomination(item.getDenomination())
                                            .saleValue(cartItem.getQuantitySale())
                                            .loadValue(cartItem.getQuantityLoad())
                                            .unloadValue(cartItem.getQuantityUnload())
                                            .itemName(item.getItemName())
                                            .itemId(cartItem.getItemId())
                                            .loadStatus(cartItem.getLoadStatus())
                                            .unloadStatus(cartItem.getUnloadStatus())
                                            .saleStatus(cartItem.getSaleStatus())
                                            .build();
                itemPojos.add(itemPojo);
            }
            catch (ServiceException e) {
                log.error("Error while getting item details for item id: " + cartItem.getItemId(),
                    e
                );
                throw e;
            }
            catch (Exception e) {
                log.error("Error while getting item details for item id: " + cartItem.getItemId(),
                    e
                );
                throw new ServiceException(
                    "Error while getting item details" + e);
            }
        }
        return itemPojos;
    }

    @Override
    public LocalDate getLatestProcessedDateForCart (
        long substationId, String cartPlateNumber, CartStatus cartStatus)
        throws ServiceException
    {

        switch (cartStatus) {
        case LOAD:
            return cartItemRepository.findLatestLoadedDateForCart(substationId,
                cartPlateNumber
            );
        case UNLOAD:
            return cartItemRepository.findLatestUnloadedDateForCart(substationId,
                cartPlateNumber
            );
        case SALEDATA:
            return cartItemRepository.findLatestSaleDataDateForCart(substationId,
                cartPlateNumber
            );
        default:
            throw new ServiceException("Invalid cart status");

        }
    }
}
