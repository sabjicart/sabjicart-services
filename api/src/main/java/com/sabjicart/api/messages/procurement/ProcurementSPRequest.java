
package com.sabjicart.api.messages.procurement;

import com.sabjicart.api.messages.inoutbound.Request;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProcurementSPRequest extends Request
{
    String substationId;
    LocalDate date;
    List<ProcurementSPPojo> procurementSPPojos;
}
