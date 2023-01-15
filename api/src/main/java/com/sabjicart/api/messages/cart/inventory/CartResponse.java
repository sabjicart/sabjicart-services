
package com.sabjicart.api.messages.cart.inventory;

import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.shared.CartStatus;
import com.sabjicart.api.shared.ItemInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse extends Response
{
    long substationId;
    String cartPlateNumber;
    List<ItemInfo> itemInfoList;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate onDate;
    @Enumerated(EnumType.STRING)
    CartStatus currentProcessStatus;

    public CartResponse ()
    {
        itemInfoList = new ArrayList<>();
    }
}
