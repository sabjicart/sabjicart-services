
package com.sabjicart.api.messages.procurement;

import com.sabjicart.api.messages.inoutbound.Response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProcurementResponse extends Response
{
    List<ProcurementInfo> procurementInfos;
}
