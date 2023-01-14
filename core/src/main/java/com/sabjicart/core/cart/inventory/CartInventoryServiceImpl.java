
package com.sabjicart.core.cart.inventory;

import com.sabjicart.api.cart.inventory.CartInventoryService;
import com.sabjicart.api.cart.inventory.LoadService;
import com.sabjicart.api.cart.inventory.SaleService;
import com.sabjicart.api.cart.inventory.UnloadHelperService;
import com.sabjicart.api.cart.inventory.UnloadService;
import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.cart.inventory.CartRequest;
import com.sabjicart.api.messages.cart.inventory.CartResponse;
import com.sabjicart.api.messages.common.CartItemInfoService;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.shared.CartStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class CartInventoryServiceImpl implements CartInventoryService
{

    LoadService loadService;

    UnloadService unloadService;

    SaleService saleService;

    CartItemInfoService cartItemInfoService;
    UnloadHelperService unloadHelperService;

    @Override
    public CartResponse getCartItemsAsOfDate (
        long substation, String cartNumber, LocalDate onDate, CartStatus status)
        throws ServiceException
    {

        // Declaration
        CartResponse response;
        boolean isCartOnBoarding;

        // Initialisation
        isCartOnBoarding =
            false;  // flag to indicate adding of new cart at substation

        if (null == onDate) {
            // set onDate to the latest processed date for the cart
            onDate = cartItemInfoService.getLatestProcessedDateForCart(
                substation,
                cartNumber,
                status
            );
            if (null == onDate) {
                isCartOnBoarding =
                    true; // setting true to indicate cart onboarding process at the substation
            }
        }

        switch (status) {
        case LOAD:
            return loadService.fetchLoadedItems(substation, cartNumber, onDate);
        case UNLOAD:
            if (!isCartOnBoarding && onDate.isEqual(LocalDate.now())) {
                response = unloadService.fetchUnloadedItems(substation,
                    cartNumber,
                    onDate
                );
            }
            else {
                response = loadService.fetchLoadedItems(substation,
                    cartNumber,
                    LocalDate.now()
                );
            }
            return unloadHelperService.cartResponseExpectedReturnInflater(
                response);
        case SALEDATA:
            if (!isCartOnBoarding && onDate.isEqual(LocalDate.now())) {
                return saleService.fetchSaleData(substation,
                    cartNumber,
                    onDate
                );
            }
            else
                return loadService.fetchLoadedItems(substation,
                    cartNumber,
                    LocalDate.now()
                );
        default:
            throw new ServiceException("Invalid cart status");

        }
    }

    @Override
    public Response updateCartItems (
        CartRequest cartLoadRequest, CartStatus status) throws ServiceException
    {
        switch (status) {
        case LOAD:
            return loadService.updateLoadedItems(cartLoadRequest);
        case UNLOAD:
            return unloadService.updateUnloadedItems(cartLoadRequest);
        case SALEDATA:
            return saleService.updateSaleData(cartLoadRequest);
        default:
            throw new ServiceException("Invalid cart status");

        }
    }
}
