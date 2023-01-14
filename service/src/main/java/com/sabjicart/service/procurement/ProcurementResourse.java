
package com.sabjicart.service.procurement;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.procurement.ProcurementRequest;
import com.sabjicart.api.messages.procurement.ProcurementResponse;
import com.sabjicart.api.messages.procurement.ProcurementSPRequest;
import com.sabjicart.api.procurement.ProcurementService;
import com.sabjicart.api.procurement.selling.price.ProcurementSPService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class ProcurementResourse
{
    ProcurementService procurementService;
    ProcurementSPService procurementSPService;

    @GetMapping("/procurement")
    public ResponseEntity<ProcurementResponse> getProcurement (
        @RequestParam("substationId")
        String substationId,
        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date)
    {
        ProcurementResponse response = new ProcurementResponse();
        try {
            response = procurementService.getProcurement(substationId, date);
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/procurement")
    public ResponseEntity<Response> updateProcurement (
        @RequestBody
        @Valid ProcurementRequest procurementRequest)
    {
        Response response = new Response();
        try {
            response = procurementService.updateProcurement(procurementRequest);
        }
        catch (ServiceException e) {
            ResponseUtil.error(response, e.getMessage());
            return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/procurement/sp")
    public ResponseEntity<Response> updateProcurementSP (
        @RequestBody
        @Valid ProcurementSPRequest procurementSPRequest)
    {
        Response response = new Response();
        try {
            response =
                procurementSPService.updateSellingPrice(procurementSPRequest);
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
