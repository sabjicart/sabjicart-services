
package com.sabjicart.service.cart.inventory;

import com.sabjicart.api.cart.inventory.CartInventoryService;
import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.cart.inventory.CartRequest;
import com.sabjicart.api.messages.cart.inventory.CartResponse;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.shared.CartStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class CartInventoryResource
{

    CartInventoryService cartInventoryService;

    @GetMapping(value = "/support/cart/{status}")
    public ResponseEntity<CartResponse> getCartItemsAsOfDate (
        @RequestParam(value = "substationID")
        long substationId,
        @RequestParam(value = "cartPlateNumber")
        @NotEmpty(message = "please provide a valid cart number") String cartPlateNumber,
        @RequestParam(value = "onDate", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate onDate,
        @PathVariable
        CartStatus status)
    {

        // Declaration
        CartResponse response = new CartResponse();

        try {
            response = cartInventoryService.getCartItemsAsOfDate(substationId,
                cartPlateNumber,
                onDate,
                status
            );
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/support/cart/{status}")
    public ResponseEntity<Response> updateCartItemsAsOfDate (
        @RequestBody
        @Valid CartRequest cartLoadRequest,
        @PathVariable
        CartStatus status)
    {
        // Declaration
        Response response = new Response();

        try {
            response =
                cartInventoryService.updateCartItems(cartLoadRequest, status);
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
