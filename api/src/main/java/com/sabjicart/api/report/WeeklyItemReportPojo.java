
package com.sabjicart.api.report;

import com.sabjicart.api.shared.Denomination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyItemReportPojo
{
    String itemId;
    String itemName;
    @Enumerated(EnumType.STRING)
    Denomination denomination;
    double averageLastWeekSale;

}
