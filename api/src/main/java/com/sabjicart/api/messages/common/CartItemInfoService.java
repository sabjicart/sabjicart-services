package com.sabjicart.api.messages.common;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.model.CartItem;
import com.sabjicart.api.shared.CartStatus;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface CartItemInfoService
{
    /**
     * Interface to get cart item info
     * @param substationId
     * @param cartPlateNumber
     * @param date
     * @return
     */
    List<ItemPojo> getCartItemInfo (
        long substationId, String cartPlateNumber, LocalDate date)
        throws ServiceException;

    /**
     * Interface to get cart item info
     * @param substationId
     * @param date
     * @return
     * @throws ServiceException
     */
    List<ItemPojo> getCartItemInfo (
        long substationId, LocalDate date) throws ServiceException;

    /**
     * Interface to get unique item pojos with sum of data of similar item pojos from a item pojos list
     * @param itemPojos
     * @return
     * @throws ServiceException
     */
    HashMap<String, ItemPojo> itemPojosAdder (List<ItemPojo> itemPojos)
        throws ServiceException;

    /**
     * Interface to convert a list of CartItems to ItemPojos
     * @param cartItems
     * @return
     * @throws ServiceException
     */
    List<ItemPojo> cartItemToItemPojoConverter (List<CartItem> cartItems)
        throws ServiceException;

    /**
     * Interface to get latest processed date for a cart process LOAD< UNLOAD, SALEDATA
     * @param substationId
     * @param cartPlateNumber
     * @param cartStatus
     * @return
     */
    LocalDate getLatestProcessedDateForCart (
        long substationId, String cartPlateNumber, CartStatus cartStatus)
        throws ServiceException;

    List<ItemPojo> getCartItemInfo (String cartPlateNumber, String itemId) throws ServiceException;
}
