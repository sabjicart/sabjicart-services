
package com.sabjicart.api.model;

import com.sabjicart.api.parser.LocalDateConverter;
import com.sabjicart.api.shared.Currency;
import com.sabjicart.api.shared.TransactionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AccountTransaction extends AbstractBaseEntity
{
    String refId;
    String comment;
    double amount;
    @Enumerated(EnumType.STRING)
    TransactionType transactionType;
    String creator;
    @Convert(converter = LocalDateConverter.class)
    LocalDate date;
    @Enumerated(EnumType.STRING)
    Currency currency;
    String category;

}
