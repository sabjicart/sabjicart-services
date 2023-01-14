
package com.sabjicart.service.metadata.cart;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.metadata.cart.ItemMetaRequest;
import com.sabjicart.api.messages.metadata.cart.ItemMetaResponse;
import com.sabjicart.api.metadata.cart.ItemMetaService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class ItemMetaResource
{

    ItemMetaService itemMetaService;

    @GetMapping(value = "/support/item/meta")
    public ResponseEntity<ItemMetaResponse> getItemMetadata ()
    {
        // Declaration
        ItemMetaResponse response = new ItemMetaResponse();

        try {
            response = itemMetaService.getItemMetadata();
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/support/item/meta")
    public ResponseEntity<Response> updateItemMetaData (

        @RequestBody
        @Valid ItemMetaRequest itemMetaRequest)
    {
        // Declaration
        Response response = new Response();

        try {
            response = itemMetaService.updateItemMetadata(itemMetaRequest);
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/support/item/meta/active")
    public ResponseEntity<ItemMetaResponse> getActiveItem ()
    {
        ItemMetaResponse response = new ItemMetaResponse();

        try {
            response = itemMetaService.getActiveItem();
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
