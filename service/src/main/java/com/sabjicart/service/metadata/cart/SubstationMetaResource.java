
package com.sabjicart.service.metadata.cart;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.metadata.cart.SubstationCartResponse;
import com.sabjicart.api.messages.metadata.cart.SubstationResponse;
import com.sabjicart.api.metadata.cart.SubstationMetaService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class SubstationMetaResource
{
    SubstationMetaService substationMetaService;

    @GetMapping(value = "/support/substation/carts")
    public ResponseEntity<SubstationCartResponse> getCarts (
        @RequestParam(value = "substationId")
        @NotEmpty(message = "please provide a valid substation") Long substationId)
    {
        // Declaration
        SubstationCartResponse response = new SubstationCartResponse();

        try {
            response = substationMetaService.getCarts(substationId);
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/support/substation")
    public ResponseEntity<SubstationResponse> getSubstations ()
    {
        // Declaration
        SubstationResponse response = new SubstationResponse();

        try {
            response = substationMetaService.getSubstations();
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
