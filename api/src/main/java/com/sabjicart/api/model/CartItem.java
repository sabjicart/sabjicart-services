
package com.sabjicart.api.model;

import com.sabjicart.api.parser.LocalDateConverter;
import com.sabjicart.api.shared.CartProgressStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem extends AbstractBaseEntity
{

    String cartPlateNumber;
    long substationId;
    String itemId;
    @Convert(converter = LocalDateConverter.class)
    LocalDate processingDate;
    double quantityLoad;
    double quantityUnload;
    double quantitySale;
    @Enumerated(EnumType.STRING)
    CartProgressStatus loadStatus;
    @Enumerated(EnumType.STRING)
    CartProgressStatus unloadStatus;
    @Enumerated(EnumType.STRING)
    CartProgressStatus saleStatus;
    LocalDateTime timeLoaded;
    LocalDateTime timeUnloaded;
    LocalDateTime timeSaleUpdated;

}
