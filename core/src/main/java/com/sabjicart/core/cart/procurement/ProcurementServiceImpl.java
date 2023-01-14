
package com.sabjicart.core.cart.procurement;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.procurement.ProcurementPojo;
import com.sabjicart.api.messages.procurement.ProcurementRequest;
import com.sabjicart.api.messages.procurement.ProcurementResponse;
import com.sabjicart.api.model.Procurement;
import com.sabjicart.api.procurement.ProcurementService;
import com.sabjicart.core.cart.repository.ProcurementRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@Slf4j
public class ProcurementServiceImpl implements ProcurementService
{
    ProcurementRepository procurementRepository;

    @Override
    public Response updateProcurement (ProcurementRequest request)
        throws ServiceException
    {
        Response response = new Response();
        String substationId = request.getSubstationId();
        LocalDate date = request.getDate();
        List<Procurement> existingProcurements;
        List<Procurement> procurementsToBeUpdated = new ArrayList<>();
        List<Procurement> procurementsToBeDeleted = new ArrayList<>();
        HashSet<String> itemIdSet = new HashSet<>();
        HashMap<String, Procurement> procurementHashMap = new HashMap<>();
        try {
            existingProcurements =
                procurementRepository.findAllBySubstationIdAndProcurementDate(
                    substationId,
                    date
                );
            for (ProcurementPojo procurementPojo : request.getProcurementPojos()) {
                itemIdSet.add(procurementPojo.getItemId());
            }

            //Filtering procurements to be updated and deleted
            for (Procurement procurement : existingProcurements) {
                if (!itemIdSet.contains(procurement.getItemId())) {
                    procurementsToBeDeleted.add(procurement);
                }
                else {
                    procurementHashMap.put(procurement.getItemId(),
                        procurement
                    );
                }
            }
            procurementRepository.deleteAll(procurementsToBeDeleted);
            log.info(
                "Deleting {} procurements for substation:{},procurementDate:{} ,items:{}",
                procurementsToBeDeleted.size(),
                substationId,
                date,
                procurementsToBeDeleted
            );
            for (ProcurementPojo procurementPojo : request.getProcurementPojos()) {
                Procurement procurement;
                if (procurementHashMap.containsKey(procurementPojo.getItemId())) {
                    procurement =
                        procurementHashMap.get(procurementPojo.getItemId());
                    procurement.setQuantity(procurementPojo.getQuantity());
                    procurement.setCostPrice(procurementPojo.getCostPrice());
                }
                else {
                    procurement = Procurement.builder()
                                             .substationId(substationId)
                                             .procurementDate(date)
                                             .itemId(procurementPojo.getItemId())
                                             .quantity(procurementPojo.getQuantity())
                                             .costPrice(procurementPojo.getCostPrice())
                                             .build();
                    log.info(
                        "Adding new procurement for substation:{},procurementDate:{},item:{}",
                        substationId,
                        date,
                        procurement
                    );
                }
                procurementsToBeUpdated.add(procurement);
            }
            procurementRepository.saveAll(procurementsToBeUpdated);

            log.info(
                "Updating {} procurements for substation:{},procurementDate:{}",
                procurementsToBeUpdated.size(),
                substationId,
                date
            );
        }
        catch (Exception e) {
            log.error(
                "Error while updating procurement for substationId :{},procurementDate :{}",
                substationId,
                date,
                e
            );
            throw new ServiceException("Error while updating procurement", e);
        }
        ResponseUtil.success(response);
        return response;
    }

    @Override
    public ProcurementResponse getProcurement (
        String substationId, LocalDate date) throws ServiceException
    {
        ProcurementResponse response = new ProcurementResponse();
        try {
            List<Procurement> procurements =
                procurementRepository.findAllBySubstationIdAndProcurementDate(
                    substationId,
                    date
                );
            response.setProcurementInfos(Procurement.procurementInfoInflater(
                procurements));
        }
        catch (Exception e) {
            log.error("Error getting procurement");
            throw new ServiceException("Error getting procurement");
        }
        ResponseUtil.success(response);
        return response;
    }
}
