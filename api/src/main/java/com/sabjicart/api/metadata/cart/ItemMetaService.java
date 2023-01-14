
package com.sabjicart.api.metadata.cart;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.metadata.cart.ItemMetaPojo;
import com.sabjicart.api.messages.metadata.cart.ItemMetaRequest;
import com.sabjicart.api.messages.metadata.cart.ItemMetaResponse;
import com.sabjicart.api.model.Item;

import java.util.List;

public interface ItemMetaService
{

    /**
     * Interface to fetch static item lists for cart loading
     * @return
     * @throws ServiceException
     */
    ItemMetaResponse getItemMetadata () throws ServiceException;

    /**
     * Interface to update static item lists for cart loading
     * @param itemMetaRequest
     * @return
     * @throws ServiceException
     */

    Response updateItemMetadata (ItemMetaRequest itemMetaRequest)
        throws ServiceException;

    /**
     * Interface to fetch attached active items
     * @return
     * @throws ServiceException
     */
    ItemMetaResponse getActiveItem () throws ServiceException;

    /**
     * Interface to inflate ItemMetaPojo list using Item List
     * @param items
     * @return
     * @throws ServiceException
     */
    List<ItemMetaPojo> itemMetaPojoInflater (List<Item> items)
        throws ServiceException;

}
