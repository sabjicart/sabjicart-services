
package com.sabjicart.api.report;

import com.sabjicart.api.shared.Denomination;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportCartItemPojo
{
    String itemId;
    String itemName;
    double loadValue;
    double unloadValue;
    double saleValue;
    double anomaly;
    LocalDate date;
    Denomination denomination;
}
