
package com.sabjicart.api.messages.metadata.cart;

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
public class ItemMetaPojo
{
    String itemId;
    String itemName;
    @Enumerated(EnumType.STRING)
    Denomination denomination;
    String itemDescription;

}
