
package com.sabjicart.api.messages.procurement;

import com.sabjicart.api.model.Procurement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProcurementInfo
{
    String itemId;
    double quantity;
    double costPrice;
    Double unitSellingPrice;

    public ProcurementInfo (Procurement procurement)
    {
        this.itemId = procurement.getItemId();
        this.quantity = procurement.getQuantity();
        this.costPrice = procurement.getCostPrice();
        this.unitSellingPrice = procurement.getUnitSellingPrice();
    }
}
