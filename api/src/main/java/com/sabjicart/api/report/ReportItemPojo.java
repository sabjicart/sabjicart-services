/*

    Copyright (c) 2022 PySphere Technologies LLP.

    All rights reserved. Patents pending.


    Responsible: Ayush Kumar

*/
package com.sabjicart.api.report;

import com.sabjicart.api.shared.Denomination;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportItemPojo
{
    String itemId;
    String itemName;
    double loadValue;
    double unloadValue;
    double saleValue;
    double anomaly;
    Denomination denomination;
}
