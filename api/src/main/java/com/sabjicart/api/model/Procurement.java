
package com.sabjicart.api.model;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.procurement.ProcurementInfo;
import com.sabjicart.api.parser.LocalDateConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Convert;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Procurement extends AbstractBaseEntity
{
    String itemId;
    double quantity;
    double costPrice;
    Double unitSellingPrice;
    @Convert(converter = LocalDateConverter.class)
    LocalDate procurementDate;
    String substationId;

    public static List<ProcurementInfo> procurementInfoInflater (List<Procurement> procurements)
        throws ServiceException
    {
        List<ProcurementInfo> procurementInfos = new ArrayList<>();
        for (Procurement procurement : procurements) {
            try {
                procurementInfos.add(new ProcurementInfo(procurement));
            }
            catch (Exception e) {
                log.error(
                    "Error inflating procurement infos for procurement itemId: {} , procurementDate: {}, substationId: {}",
                    procurement.getItemId(),
                    procurement.getProcurementDate(),
                    procurement.getSubstationId()
                );
                throw new ServiceException("Error getting Procurement info");
            }
        }
        return procurementInfos;
    }
}
