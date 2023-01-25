
package com.sabjicart.api.messages.common;

import com.sabjicart.api.shared.CartProgressStatus;
import com.sabjicart.api.shared.Denomination;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemPojo
{
    String itemId;
    String itemName;
    double loadValue;
    double unloadValue;
    double saleValue;
    @Enumerated(EnumType.STRING)
    CartProgressStatus loadStatus;
    @Enumerated(EnumType.STRING)
    CartProgressStatus unloadStatus;
    @Enumerated(EnumType.STRING)
    CartProgressStatus saleStatus;
    Denomination denomination;
    LocalDate date;
}
