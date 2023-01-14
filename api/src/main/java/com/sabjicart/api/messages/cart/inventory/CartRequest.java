
package com.sabjicart.api.messages.cart.inventory;

import com.sabjicart.api.messages.inoutbound.Request;
import com.sabjicart.api.shared.ItemInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest extends Request
{
    long substationId;
    String cartNumber;
    LocalDate onDate;
    List<ItemInfo> itemInfoList;
}
