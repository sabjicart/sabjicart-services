
package com.sabjicart.core.cart.inventory;

import com.sabjicart.api.cart.inventory.UnloadHelperService;
import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.cart.inventory.CartResponse;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.model.CartItem;
import com.sabjicart.api.shared.CartProgressStatus;
import com.sabjicart.api.shared.UnloadItemInfo;
import com.sabjicart.core.cart.repository.CartItemRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@Slf4j
public class UnloadHelperServiceImpl implements UnloadHelperService
{
    CartItemRepository cartItemRepository;

    @Override
    public CartResponse cartResponseExpectedReturnInflater (CartResponse cartResponse)
        throws ServiceException
    {
        long substationId = cartResponse.getSubstationId();
        String cartPlateNumber = cartResponse.getCartPlateNumber();
        LocalDate onDate = cartResponse.getOnDate();
        List<UnloadItemInfo> unloadItemInfos =
            cartResponse.getUnloadItemInfoList();
        HashMap<String, CartItem> cartItemHashMap = new HashMap<>();
        try {

            List<CartItem> cartItems =
                cartItemRepository.findAllBySubstationIdAndCartPlateNumberAndProcessingDateAndSaleStatus(
                    substationId,
                    cartPlateNumber,
                    onDate,
                    CartProgressStatus.COMPLETED
                );
            for (CartItem cartItem : cartItems) {
                cartItemHashMap.put(cartItem.getItemId(), cartItem);
            }
            for (int i = 0; i < unloadItemInfos.size(); i++) {
                String itemId = unloadItemInfos.get(i).getItemId();
                if (cartItemHashMap.containsKey(itemId)) {
                    CartItem cartItem = cartItemHashMap.get(itemId);
                    unloadItemInfos.get(i)
                                   .setExpectedReturn(cartItem.getQuantityLoad()
                                       - cartItem.getQuantitySale());
                }
                else {
                    unloadItemInfos.get(i).setExpectedReturn(null);
                }
            }
        }
        catch (Exception e) {
            log.error(
                "Error while fetching unloaded items for substationId={} cart={} date={}",
                cartPlateNumber,
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
}
