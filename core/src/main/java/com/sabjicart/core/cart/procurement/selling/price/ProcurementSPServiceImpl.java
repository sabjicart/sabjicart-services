
package com.sabjicart.core.cart.procurement.selling.price;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.procurement.ProcurementSPPojo;
import com.sabjicart.api.messages.procurement.ProcurementSPRequest;
import com.sabjicart.api.model.Procurement;
import com.sabjicart.api.procurement.selling.price.ProcurementSPService;
import com.sabjicart.core.cart.repository.ProcurementRepository;
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
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@Slf4j
public class ProcurementSPServiceImpl implements ProcurementSPService
{

    ProcurementRepository procurementRepository;

    @Override
    public Response updateSellingPrice (ProcurementSPRequest request)
        throws ServiceException
    {
        Response response = new Response();
        String substationId = request.getSubstationId();
        LocalDate date = request.getDate();
        List<Procurement> procurements;
        HashMap<String, Procurement> procurementHashMap = new HashMap<>();
        List<Procurement> updatedProcurements = new ArrayList<>();
        try {
            procurements =
                procurementRepository.findAllBySubstationIdAndProcurementDate(
                    substationId,
                    date
                );
            for (Procurement procurement : procurements) {
                procurementHashMap.put(procurement.getItemId(), procurement);
            }
            for (ProcurementSPPojo procurementSPPojo : request.getProcurementSPPojos()) {
                if (procurementHashMap.containsKey(procurementSPPojo.getItemId())) {
                    Procurement procurement =
                        procurementHashMap.get(procurementSPPojo.getItemId());
                    procurement.setUnitSellingPrice(procurementSPPojo.getUnitSellingPrice());
                    updatedProcurements.add(procurement);
                }
                else {
                    log.error(
                        "Item with id {} not found in procurement for substation :{} and date :{}",
                        procurementSPPojo.getItemId(),
                        substationId,
                        date
                    );
                    throw new ServiceException(
                        "Error in updating selling price in procurement for item :"
                            + procurementSPPojo.getItemId());
                }
            }
            procurementRepository.saveAll(updatedProcurements);
        }
        catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error(
                "Error updating selling price for substation :{} and date :{}",
                substationId,
                date
            );
            throw new ServiceException("Error updating selling price");
        }
        ResponseUtil.success(response);
        return response;
    }
}
