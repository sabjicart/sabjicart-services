

package com.sabjicart.core.cart.metadata.cart;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.metadata.cart.ItemMetaPojo;
import com.sabjicart.api.messages.metadata.cart.ItemMetaRequest;
import com.sabjicart.api.messages.metadata.cart.ItemMetaResponse;
import com.sabjicart.api.metadata.cart.ItemMetaService;
import com.sabjicart.api.model.Item;
import com.sabjicart.core.cart.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class ItemMetaServiceImpl implements ItemMetaService
{
    ItemRepository itemRepository;

    @Override
    public ItemMetaResponse getItemMetadata () throws ServiceException
    {
        ItemMetaResponse itemMetaResponse = new ItemMetaResponse();
        List<ItemMetaPojo> itemMetaPojos;
        try {
            List<Item> items = itemRepository.findAllByOrderByItemNameAsc();
            itemMetaPojos = itemMetaPojoInflater(items);
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error("Error getting Item Meta data:{}", e.getMessage());
            throw new ServiceException(
                "Error in getItemMetadata: " + e.getMessage());
        }
        itemMetaResponse.setItemMetaPojos(itemMetaPojos);
        ResponseUtil.success(itemMetaResponse);
        return itemMetaResponse;
    }

    @Override
    public Response updateItemMetadata (ItemMetaRequest itemMetaRequest)
        throws ServiceException
    {
        Response response = new Response();
        List<Item> items = new ArrayList<>();
        try {
            for (ItemMetaPojo itemMetaPojo : itemMetaRequest.getItemMetaPojos()) {
                try {
                    Item item = Item.builder()
                                    .itemId(itemMetaPojo.getItemId())
                                    .itemDescription(itemMetaPojo.getItemDescription())
                                    .itemName(itemMetaPojo.getItemName())
                                    .denomination(itemMetaPojo.getDenomination())
                                    .build();
                    items.add(item);
                }
                catch (Exception e) {
                    log.error(
                        "Error while updating item metadata for item {}",
                        itemMetaPojo.getItemId()
                    );
                    throw new ServiceException(
                        "Error while updating item metadata");
                }
            }
            itemRepository.saveAll(items);
        }
        catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Error while updating item metadata");
            throw new ServiceException("Error while updating item metadata", e);
        }
        ResponseUtil.success(response);
        return response;
    }

    @Override
    public ItemMetaResponse getActiveItem () throws ServiceException
    {
        ItemMetaResponse itemMetaResponse = new ItemMetaResponse();
        List<ItemMetaPojo> itemMetaPojos;
        try {
            List<Item> items = itemRepository.findByActiveTrueOrderByItemNameAsc();
            itemMetaPojos = itemMetaPojoInflater(items);
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error("Error getting active Item data:{}", e.getMessage());
            throw new ServiceException(
                "Error in getActiveItem: " + e.getMessage());
        }
        itemMetaResponse.setItemMetaPojos(itemMetaPojos);
        ResponseUtil.success(itemMetaResponse);
        return itemMetaResponse;
    }

    @Override
    public List<ItemMetaPojo> itemMetaPojoInflater (List<Item> items)
        throws ServiceException
    {
        List<ItemMetaPojo> itemMetaPojos = new ArrayList<>();
        for (Item item : items) {
            try {
                ItemMetaPojo itemMetaPojo = ItemMetaPojo.builder()
                                                        .itemId(item.getItemId())
                                                        .itemDescription(item.getItemDescription())
                                                        .itemName(item.getItemName())
                                                        .denomination(item.getDenomination())
                                                        .build();
                itemMetaPojos.add(itemMetaPojo);
            }
            catch (Exception e) {
                log.error(
                    "Error while inflating item metadata for item {}",
                    item.getId()
                );
                throw new ServiceException("Error while inflating item metadata");
            }
        }
        return itemMetaPojos;
    }
}
